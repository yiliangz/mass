var YDataGrid = function(config){
		config = config || {};
		var dataGrid = config.dataGrid || {}
		//Actions
		var actionUrl =  config.action || {}
		var Action = {
			'save': actionUrl.save ||'save.do',
			'getId': actionUrl.getId||'getId.do',
			'remove': actionUrl.remove||'delete.do',
			'view':actionUrl.view||'view.do'
		}
		
		//Grid DataList
		var Grid = $('#data-list');
		var Form = {
			search:  $("#searchForm"),
			edit:    $("#editForm"),
			view:    $("#viewForm")
		};
		
		if(config.form) {
			Form.buttons = config.form.buttons;
			Form.type = config.form.window;
		}

		//Win 窗口
		var Win = {
			edit : $("#edit-win"),
			view : $("#view-win") 
		}	
		
//		$(window).keydown(function(e) {
//			switch (e.keyCode) {
//			case 27: // esc
//				Handler.simplyClose();
//				break;
//			}
//			e.stopPropagation();
//			e.preventDefault();
//		});
		
		//处理函数
		var Handler = {
			//serach 查询事件
			search: function(callback){
				Events.refresh();
				//回调函数
				if(jQuery.isFunction(callback)){
					callback();
				}
				return false;	
			},
			//add按钮事件
			add: function(callback){
				Win.edit.dialog('open');
				Form.edit.resetForm();
				//回调函数
				if(jQuery.isFunction(callback)){
					callback();
				}
			},
			//edit 按钮事件
			edit:  function(callback){
				var record = Utils.getCheckedRows();
				if (Utils.checkSelectOne(record)){
					binf.progress();
					var data ={};
					var idKey = dataGrid.idField|| 'id'; //主键名称
 					data[idKey] = (record[0][idKey]);
					binf.getById(Action.getId,data,function(result){
						binf.closeProgress();
						Form.edit.form('load',result.data);
						
						if(result.ckedit){
							 var editor = CKEDITOR.instances.editor1 ;
						      editor.setData(result.ckedit); 
						}
						 
						Win.edit.dialog('open');
						
						//回调函数
						if(jQuery.isFunction(callback)){
							callback(result);
						}
					});
				}
			},
			view: function(callback) {
				var record = Utils.getCheckedRows();
				if (Utils.checkSelectOne(record)){
					var data = {};
				
					var idKey = dataGrid.idField|| 'id'; //主键名称
 					data[idKey] = (record[0][idKey]);
 					var ajaxOptions = {
 						type:"post",
 						dataType:"text"
 					};
					binf.ajax(Action.view,data,ajaxOptions,function(result){
						Form.view.empty();
						Form.view.append(result);
						Win.view.dialog('open');
						if(jQuery.isFunction(callback)){
							callback(callback);
						}
					});
				}				
			},
			//刷新Grid 数据
			refresh: function(callback){
				var param = Form.search.serializeObject();
				param = $.extend({},dataGrid.queryParams,param);
				Grid.datagrid('reload',param);
				//回调函数
				if(jQuery.isFunction(callback)){
					callback();
				}
			},
			//删除记录
			remove: function(callback){
				var records = Utils.getCheckedRows();
				if (Utils.checkSelect(records)){
					$.messager.confirm('Confirm','确定要删除数据?',function(r){  
					    if (r){
					    	binf.progress();
					    	var arr = [],idKey = dataGrid.idField || 'id'; //主键名称
					    	$.each(records,function(i,record){
					    		arr.push('id='+record[idKey]);
					    	});
					    	var data = arr.join("&");
					   		binf.deleteForm(Action.remove,data,function(result){
								binf.closeProgress();
								Events.refresh();
								//回调函数
								if(jQuery.isFunction(callback)){
									callback(result);
								}
							});
					    }  
					});
				}
			},//保存调用方法
			save: function(callback){
				var para  ="";
				if(dataGrid.idField == 'schemaId'){
					var editor = CKEDITOR.instances.editor1;
					var myed  = editor.document.getBody().getHtml();
					    para = "?myeditor="+myed;
				}
				if(Form.edit.form('validate')){
					binf.progress();
					Form.edit.attr('action',Action.save+para);
					binf.saveForm(Form.edit,function(data){
						binf.closeProgress();
						Win.edit.dialog('close');
					    Events.refresh();
					    Form.edit.resetForm();
					     //回调函数
						if(jQuery.isFunction(callback)){
							callback(data);
						}
					});
				 }
			},
			//关闭按钮事件
			close : function (callback){
				$.messager.confirm('Confirm','Are you sure you want close Window?',function(r){  
				    if (r){  
				     	Win.edit.dialog('close');
				     	//回调函数
						if(jQuery.isFunction(callback)){
							callback(data);
						}
				    }
				});
			},
			simplyClose: function(callback) {
				Win.edit.dialog('close');
				Win.view.dialog('close');
			},
			resetButtons: function(buttons) {
//				Win.view.parent().find(".dialog-button").empty();
				var dialogButtons = Win.view.parent().find(".dialog-button");
				dialogButtons.empty();
				for (var i=0; buttons&&i<buttons.length;i++) {
					
					var button = buttons[i];
					var $button =  $("<a href='javascript:void(0)' class='l-btn l-btn-small' group='' no='" + i + "'>" +
									"<span class='l-btn-left'><span class='l-btn-text'>"+ button.text +"</span></span></a>");
					$button.bind("click",function(){
						var no = $(this).attr("no");
						buttons[no].event();
					});
					dialogButtons.append($button);
				}
			}
		}
		
		//Grid 工具类
		var Utils = {
			getCheckedRows : function(){
				return Grid.datagrid('getChecked');			
			},
			checkSelect : function(rows){//检查grid是否有勾选的行, 有返回 true,没有返回true
				var records =  rows;
				if(records && records.length > 0){
					return true;
				}
				binf.alert('警告','未选中记录.','warning');  
				return false;
				
			},
			checkSelectOne : function(rows){//检查grid是否只勾选了一行,是返回 true,否返回true
				var records = rows;
				if(!Utils.checkSelect(records)){
					return false;
				}
				if(records.length == 1){
					return true;
				}
				binf.alert('警告','只能选择一行记录.','warning');  
				return false;
			},
			downLoadTwoDiCode:function(){//下载2维码
				var record = Utils.getCheckedRows();
				if (Utils.checkSelectOne(record)){
					binf.progress();
					var idKey = dataGrid.idField|| 'id'; //主键名称
 					var objectId = (record[0][idKey]);
 					var url ="downLoadTwoDiCode.do";
 					var para  ="?objectId="+objectId+"&objectType=PROD";
 					if(dataGrid.idField == 'hairId'){//如果是发型管理
 						url = "../productInfo/downLoadTwoDiCode.do"
 						para  ="?objectId="+objectId+"&objectType=HAIR";
 					}
 					
// 					window.location.href(url+para,"_blank");
 					window.open(url+para,"_blank");
 					binf.closeProgress();
				}
			}
		}
		
		
		//自定义事件
		var evt= config.event || {};
		
		
		//默认事件
		var Events ={
			//serach 查询事件
			search: evt.search || Handler.search,
			//add按钮事件
			add: evt.add || Handler.add,
			//edit 按钮事件
			edit: evt.edit || Handler.edit,
			view: evt.view || Handler.view,
			//刷新Grid 数据
			refresh: evt.refresh || Handler.refresh,
			//删除记录
			remove: evt.remove || Handler.remove,
			//保存调用方法
			save: evt.save || Handler.save,
			//关闭按钮事件
			close : evt.close ||  Handler.close
		}
		
		//按钮控制 btnType 用来控制按钮是否显示,后台根据授权控制是否显示
		var bar_add ={	
						id:'btnadd',
						text:'增加',
						iconCls:'icon-add',
						btnType:'add',
						handler: Events.add
					 };
		var bar_edit = {
							id:'btnedit',
							text:'修改',
							iconCls:'icon-edit',
							btnType:'edit',
							handler: Events.edit
						};
		var bar_remove = { 
			id:'btnremove',
			text:'删除',
			iconCls:'icon-remove',
			btnType:'remove',
			handler:Events.remove
		};
		var bar_view = { 
			id:'btnView',
			text:'查看',
			iconCls:'icon-edit',
			btnType:'view',
			handler:Events.view
		};
		var toolbarConfig = [bar_add,bar_edit,bar_remove];
		var getToolbar = function (){
			var tbars = [];
			if (dataGrid.toolbar != undefined && dataGrid.toolbar.length > 0) {
				for ( var i = 0; i < dataGrid.toolbar.length; i++) {
					var bar = dataGrid.toolbar[i];
					if(!bar){
						continue;
					}
					if(bar.btnType=='add'){
						tbars.push({id:bar.id || bar_add.id,text:bar.text || bar_add.text ,iconCls: bar.iconCls || bar_add.iconCls, btnType: bar.btnType || bar_add.btnType,handler:bar.handler || bar_add.handler});
						continue;
					}
					if(bar.btnType=='edit'){
						tbars.push({id:bar.id || bar_edit.id,text:bar.text || bar_edit.text ,iconCls: bar.iconCls || bar_edit.iconCls,btnType: bar.btnType || bar_edit.btnType,handler:bar.handler || bar_edit.handler});
						continue;
					}
					if(bar.btnType=='remove'){
						tbars.push({id:bar.id || bar_remove.id,text:bar.text || bar_remove.text ,iconCls: bar.iconCls || bar_remove.iconCls,btnType: bar.btnType || bar_remove.btnType,handler:bar.handler || bar_remove.handler});
						continue;
					}
					if(bar.btnType == 'view'){
						tbars.push({id:bar.id || bar_view.id,text:bar.text || bar_view.text ,iconCls: bar.iconCls || bar_view.iconCls,btnType: bar.btnType || bar_view.btnType,handler:bar.handler || bar_view.handler});
						continue;
					}
					tbars.push({id:bar.id,text:bar.text,iconCls:bar.iconCls,btnType: bar.btnType,handler:bar.handler,disabled:bar.disabled});
				}
			}else{
				tbars = toolbarConfig;
				if (dataGrid.hasView) {
					tbars.push(bar_view);
				}
			}
			return tbars;
		}
	
		//初始化表格
		var initGrid = function(){
			var initbar= "";
			var dataconfig = {
				title: dataGrid.title || 'Data List',
				iconCls: dataGrid.iconCls || 'icon-save',
				fit:true,
				//height: dataGrid.height || 365,
				border:false,
				nowrap: true,
				autoRowHeight: false,
				striped: false,
				collapsible:false,
				remoteSort: false,
				pagination:true,
				rownumbers:true,
				singleSelect:false,
				checkOnSelect:false,
				selectOnCheck:false,
				url: dataGrid.url,
				method: dataGrid.method || 'post',
				loadMsg: dataGrid.loadMsg || 'Loading in ...',
				idField: dataGrid.idField,
				queryParams: dataGrid.queryParams || {},
				columns: dataGrid.columns,
				onDblClickRow: dataGrid.onDblClickRow || function(){
					
				},
				onLoadSuccess: dataGrid.onLoadSuccess || function(){
					Grid.datagrid('unselectAll');
					Grid.datagrid('uncheckAll');
				},
				onSelect:function(rowIndex, rowData){
					//选择一行
					var rows = Grid.datagrid('getRows');
					$.each(rows,function(i){
						if(i != rowIndex){
							Grid.datagrid('uncheckRow',i);
							Grid.datagrid('unselectRow',i);
						}
					})
					Grid.datagrid('checkRow',rowIndex);
				}
			};
			Grid.datagrid(dataconfig);
		}
		//初始化Grid按钮 按钮控制
		var initTbar = function(){
			var tbars = getToolbar();
			var _url = urls['goodsUrl'] + '/getActionBtn.do';
			var data = {'url':window.location.href};
			//查询页面授权的btnType
			binf.ajaxJson(_url,data,function(data){
				if(data.success){
					if(data.allType){
						Grid.datagrid({'toolbar':tbars});
					}else{
						var newBars = [];
						jQuery.inArray("John", data.types);				
						for(var i=0;i< tbars.length; i++){
							var bar = tbars[i];
							//btnType 为空显示
							if(!bar.btnType){
								newBars.push(bar);
							}else{
								//判断btnType是否存在,存在则显示
								if($.inArray(bar.btnType, data.types) >= 0 ){
									newBars.push(bar);
								}	
							}
						}
						if(newBars.length > 0){
							Grid.datagrid({'toolbar':newBars});
						}
					}
				}else{
					binf.alert('提示',data.msg);
				}
			});
		}
		
		//初始话form
		var initForm = function(){
			if(Form.search && Form.search[0]){
				Form.search.find("#btn-search").click(Events.search); //保存事件
			}
		}
		
		var initWin = function(){
			if(Win.edit && Win.edit[0]){
				//判断页面是否设置buttons，如果没有设置默认按钮
				var btns = Win.edit.attr("buttons");
				if(!btns) {
					Win.edit.dialog({
						buttons:[
							{
								text:'保存',
								handler:Events.save
							},{
								text:'关闭',
								handler:Events.close
							}
						]
					});
				}
			}
			Win.view.dialog({
				buttons:[
					{
						text:'保存',
						handler:Events.save
					},{
						text:'关闭',
						handler:Events.close
					}
				]			
			});
		}
		
		//this 返回属性
		this.win = Win;
		this.form = Form;
		this.grid = Grid;
		this.utils = Utils;
		this.handler = Handler;
		this.action = Action;
		
		
		//初始化方法
		this.init = function(){
			initGrid();
			initTbar();
			initForm();
			initWin();
		}
		//调用初始化
		return this;
		
};

var myformatter = function(date) {
	var y = date.getFullYear();
	var m = date.getMonth() + 1;
	var d = date.getDate();
	return y + '-' + (m < 10 ? ('0' + m) : m) + '-' + (d < 10 ? ('0' + d) : d);
}
var myparser = function(s) {
	if (!s)
		return new Date();
	var ss = (s.split('-'));
	var y = parseInt(ss[0], 10);
	var m = parseInt(ss[1], 10);
	var d = parseInt(ss[2], 10);
	if (!isNaN(y) && !isNaN(m) && !isNaN(d)) {
		return new Date(y, m - 1, d);
	} else {
		return new Date();
	}
}