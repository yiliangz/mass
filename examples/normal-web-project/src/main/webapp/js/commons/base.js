$package('binf');
var binf={
	/*Json 工具类*/
	isJson:function(str){
		var obj = null; 
		try{
			obj = binf.paserJson(str);
		}catch(e){
			return false;
		}
		var result = typeof(obj) == "object" && Object.prototype.toString.call(obj).toLowerCase() == "[object object]" && !obj.length; 
		return result;
	},
	paserJson:function(str){
		return eval("("+str+")");
	},
	/*弹出框*/
	alert:function(title, msg, icon, callback){
		$.messager.alert(title,msg,icon,callback);
	},
	/*弹出框*/
	confirm:function(title, msg,callback){
		$.messager.confirm(title,msg,callback);
	},
	progress:function(title,msg){
		 var win = $.messager.progress({  
            title: title ||'Please waiting',  
            msg: msg ||'Loading data...'  
         }); 
	},
	closeProgress:function(){
		$.messager.progress('close');
	},
	slide: function(msg,title) {

	     $.messager.show({
             title:title||'提示',
             msg:msg,
             timeout:2000,
             showType:'slide'
         });
	},
	/*重新登录页面*/
	toLogin:function(){
		window.top.location= urls['goodsUrl']+"/login.shtml";
	},
	checkLogin:function(data){//检查是否登录超时
		if(data.logoutFlag){
			binf.closeProgress();
			binf.alert('提示',"登录超时,点击确定重新登录.",'error',binf.toLogin);
			return false;
		}
		return true;
	},
	ajaxSubmit:function(form,option){
		form.ajaxSubmit(option);
	},
	ajaxJson: function(url,option,callback){
		$.ajax(url,{
			type:'post',
			 	dataType:'json',
			 	data:option,
			 	success:function(data){
			 		//坚持登录
			 		if(!binf.checkLogin(data)){
			 			return false;
			 		}		 	
			 		if($.isFunction(callback)){
			 			callback(data);
			 		}
			 	},
			 	error:function(response, textStatus, errorThrown){
			 		try{
			 			binf.closeProgress();
			 			var data = $.parseJSON(response.responseText);
				 		//检查登录
				 		if(!binf.checkLogin(data)){
				 			return false;
				 		}else{
					 		binf.alert('提示', data.msg || "请求出现异常,请联系管理员",'error');
					 	}
			 		}catch(e){
			 			binf.alert('提示',"请求出现异常,请联系管理员.",'error');
			 		}
			 	},
			 	complete:function(){
			 		
			 	}
		});
	},
	ajax:function(url,params,options,callback){
		$.ajax(url,{
			type:options.type,
		 	data:params,
		 	dataType:options.dataType,
		 	success:function(data) {
		 		if(!binf.checkLogin(data)){
		 			return false;
		 		}		 	
		 		if($.isFunction(callback)){
		 			callback(data);
		 		}
		 	}
		});		
	},
	postJSON: function(url,params,callback){
		var options = {
			type: "post",
			dataType: "json"
		};
		this.ajax(url,params,options,callback);
	},
	submitForm:function(form,callback,dataType){
			var option =
			{
			 	type:'post',
			 	dataType: dataType||'json',
			 	success:function(data){
			 		if($.isFunction(callback)){
			 			callback(data);
			 		}
			 	},
			 	error:function(response, textStatus, errorThrown){
			 		try{
			 			binf.closeProgress();
			 			var data = $.parseJSON(response.responseText);
				 		//检查登录
				 		if(!binf.checkLogin(data)){
				 			return false;
				 		}else{
					 		binf.alert('提示', data.msg || "请求出现异常,请联系管理员",'error');
					 	}
			 		}catch(e){
			 			binf.alert('提示',"请求出现异常,请联系管理员.",'error');
			 		}
			 	},
			 	complete:function(){
			 	
			 	}
			 }
			 binf.ajaxSubmit(form,option);
	},
	saveForm:function(form,callback){
		if(form.form('validate')){
			binf.progress('Please waiting','Save ing...');
			//ajax提交form
			binf.submitForm(form,function(data){
				binf.closeProgress();
			 	if(data.success){
			 		if(callback){
				       	callback(data);
				    }else{
			       		binf.alert('提示','保存成功.','info');
			        } 
		        }else{
		       	   binf.alert('提示',data.msg,'error');  
		        }
			});
		 }
	},
	/**
	 * 
	 * @param {} url
	 * @param {} option {id:''} 
	 */
	getById:function(url,option,callback){
		binf.progress();
		binf.ajaxJson(url,option,function(data){
			binf.closeProgress();
			if(data.success){
				if(callback){
			       	callback(data);
			    }
			}else{
				binf.alert('提示',data.msg,'error');  
			}
		});
	},
	deleteForm:function(url,option,callback){
		binf.progress();
		binf.ajaxJson(url,option,function(data){
				binf.closeProgress();
				if(data.success){
					if(callback){
				       	callback(data);
				    }
				}else{
					binf.alert('提示',data.msg,'error');  
				}
		});
	}
}

/* 自定义密码验证*/
$.extend($.fn.validatebox.defaults.rules, {  
    equals: {  
        validator: function(value,param){  
            return value == $(param[0]).val();  
        },  
        message: 'Field do not match.'  
    }  
});  

/*表单转成json数据*/
$.fn.serializeObject = function() {
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
        if (o[this.name]) {
            if (!o[this.name].push) {
                o[this.name] = [ o[this.name] ];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
}


/* easyui datagrid 添加和删除按钮方法 */
$.extend($.fn.datagrid.methods, {  
    addToolbarItem: function(jq, items){  
        return jq.each(function(){  
            var toolbar = $(this).parent().prev("div.datagrid-toolbar");
            for(var i = 0;i<items.length;i++){
                var item = items[i];
                if(item === "-"){
                    toolbar.append('<div class="datagrid-btn-separator"></div>');
                }else{
                    var btn=$("<a href=\"javascript:void(0)\"></a>");
                    btn[0].onclick=eval(item.handler||function(){});
                    btn.css("float","left").appendTo(toolbar).linkbutton($.extend({},item,{plain:true}));
                }
            }
            toolbar = null;
        });  
    },
    removeToolbarItem: function(jq, param){  
        return jq.each(function(){  
            var btns = $(this).parent().prev("div.datagrid-toolbar").children("a");
            var cbtn = null;
            if(typeof param == "number"){
                cbtn = btns.eq(param);
            }else if(typeof param == "string"){
                var text = null;
                btns.each(function(){
                    text = $(this).data().linkbutton.options.text;
                    if(text == param){
                        cbtn = $(this);
                        text = null;
                        return;
                    }
                });
            } 
            if(cbtn){
                var prev = cbtn.prev()[0];
                var next = cbtn.next()[0];
                if(prev && next && prev.nodeName == "DIV" && prev.nodeName == next.nodeName){
                    $(prev).remove();
                }else if(next && next.nodeName == "DIV"){
                    $(next).remove();
                }else if(prev && prev.nodeName == "DIV"){
                    $(prev).remove();
                }
                cbtn.remove();    
                cbtn= null;
            }                        
        });  
    }                 
});


////////////////////////
////  gridDlg 插件    ////
////  弹出窗口用        ////
///////////////////////
;(function($){
	var config = {
		fit:true,
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
		method: 'post',
		loadMsg:'Loading in ...',
//		title:'活动步骤',
		idField:'id',
	}
	
	function init(jq,options) {
		
		var Dlg  = $(jq);
		var Grid = $(jq).find(".binf-dlg-grid");
		
		var opts = options || {};
		
		config.onLoadSuccess = function(){
			Grid.datagrid('unselectAll');
			Grid.datagrid('uncheckAll');
		};
		config.onSelect = function(rowIndex, rowData) {
			var rows = Grid.datagrid('getRows');
			$.each(rows,function(i){
				if(i != rowIndex){
					Grid.datagrid('uncheckRow',i);
					Grid.datagrid('unselectRow',i);
				}
			})
			Grid.datagrid('checkRow',rowIndex);
		};
		
		config.onDblClickRow = function(index,row) {
			onSelectCallback(Grid,Dlg,opts.callback);
		}

		config = $.extend({},config,opts);
		
		Grid.datagrid(config);
		
		var defaultButtons = [{
			text:'选择',
			handler: function() {
				onSelectCallback(Grid,Dlg,opts.callback);
			}
		},{
			text:'返回',
			handler: function() {
				Dlg.dialog("close");
			}
		}];
		
		var buttons = opts.buttons || defaultButtons;
		
		Dlg.dialog({
			buttons: buttons
		});
		
	}
	
	function onSelectCallback(Grid,Dlg,callback) {
		var rows = Grid.datagrid("getChecked");
		if (rows.length != 1) {
			binf.slide("请选择一行");
			return;
		}
		if (jQuery.isFunction(callback)) {
			callback(rows[0]);
		}
		Dlg.dialog("close");
	}
	
	$.fn.gridDlg  = function(options,param) {
		if (typeof options == 'string'){
			return $.fn.gridDlg.methods[options](this, param);
		}	
		options = options || {};
		
		init(this,options);
		
		return this;
	}
	
	$.fn.gridDlg.methods = {
		hide: function(jq) {
			jq.dialog("close");
		},
		show: function(jq) {
			jq.dialog("open");
		},
		reload: function(jq) {
			jq.find(".binf-dlg-grid").datagrid('reload');
		}
	}
	
})(jQuery);


