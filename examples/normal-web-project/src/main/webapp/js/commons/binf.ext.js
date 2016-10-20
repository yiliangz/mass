//主要是提供扩展方法给此项目（o2o项目)
;(function($) {
	
	binf = binf || {};
	
	binf.ext = {
			
		initTypeVar: function(elementName,value,json) {
			var $element = $("[name=" + elementName + "]");
			$element.text(this.getTypeVarTextByName(value,json));
		},
		getTypeVarTextByName: function(value,json) {
			var orderStatus = typeVar.orderStatus;
			for (var i=0;i<orderStatus.length;i++) {
				if (value == orderStatus[i].value) {
					return orderStatus[i].text;
				}
			}
		},
		initAddressArea: function($area) {
			var types = ["province","city","county"];
			$area.find("select").each(function(i,element){
				binf.initAddressInput($(element),types[i]);
			});
		},
		initAddressInput: function($element,type,provinceCode,cityCode) {
			switch(type) {
				case 'province':
					$element.empty()
							.append(binf.getRegionElementHTML(binf.region.getProvinces()))
							.unbind("change")
							.change(function(){
								binf.initAddressInput($(this).next(),'city',$(this).val());
								var $county = $(this).next().next();
								if ($county.is("select")) {
									binf.initAddressInput($county,'county');
								}
							});
					break;
				case 'city':
					var html = "";
					if (!provinceCode || provinceCode == "") {
						html = binf.getRegionElementHTML();
					} else {
						html = binf.getRegionElementHTML(binf.region.getCities(provinceCode));
					}
					$element.empty()
							.append(html)
							.unbind("change")
							.change(function(){
								var $city = $(this).next();
								if ($city.is("select")) {
									binf.initAddressInput($(this).next(),'county',$(this).prev().val(),$(this).val());
								}
							});
					break;
				case 'county':
					var html = "";
					if (!cityCode || cityCode == "") {
						html = binf.getRegionElementHTML();
					} else {
						html = binf.getRegionElementHTML(binf.region.getCounties(provinceCode,cityCode));
					}	
					$element.empty().append(html);
					break;
			}
		},
		region: {
			getProvinces: function() {
				return binf.region.getRegions(regionData);
			},
			getCities:function(provinceCode) {
				var province = binf.region.getProvinceEntity(provinceCode);
				return binf.region.getRegions(province.s);
			},
			getCounties:function(provinceCode,cityCode) {
				var city = binf.region.getCityEntity(provinceCode,cityCode);
				return binf.region.getRegions(city.s);
			},
			getRegions:function(nodes) {
				var result = [];
				for (var i =0;i<nodes.length;i++) {
					result.push({code:nodes[i].c,name:nodes[i].n});
				}
				return result;				
			},
			getProvinceEntity: function(provinceCode) {
				return binf.region.getChildByCode(regionData,provinceCode);
			},
			getCityEntity: function(provinceCode,cityCode,province) {
				var province = province || binf.region.getProvinceEntity(provinceCode);
				return binf.region.getChildByCode(province.s,cityCode);			
			},
			getCountyEntity: function(provinceCode,cityCode,countyCode,city) {
				var city = city || binf.region.getCityEntity(provinceCode,cityCode);
				return binf.region.getChildByCode(city.s,countyCode);		
			},
			getChildByCode: function(children,code) {
				for (var i =0;i<children.length;i++) {
					if (children[i].c == code) {
						return children[i];
					}
				}			
			},
			getRegionEntity: function(provinceCode,cityCode,countyCode) {
				var method = binf.region;
				var province = method.getProvinceEntity(provinceCode);
				var city =     method.getCityEntity(provinceCode,cityCode,province);
				var county =   method.getCountyEntity(provinceCode,cityCode,countyCode,city);
				return {
					province: {
						code:province.c,
						name:province.n
					},
					city: {
						code: city.c,
						name: city.n
					},
					county: {
						code: county.c,
						name: county.n
					}
				};
			},
			getRegionText: function(provinceCode,cityCode,countyCode) {
				var region = binf.region.getRegionEntity(provinceCode,cityCode,countyCode);
				return region.province.name + region.city.name + region.county.name;
			}
		},
		getRegionElementHTML:function(data) {
			var html = [];
			html.push("<option value=''>请选择</option>");
			for (var i=0;data && i<data.length;i++) {
				var iterator = data[i];
				html.push("<option value='" + iterator.code + "'>"+ iterator.name + "</option>");
			}
			return html.join('');
		}
	};
	
	binf.initTypeVar           =  binf.ext.initTypeVar;
	binf.getTypeVarTextByName  =  binf.ext.getTypeVarTextByName;
	binf.initAddressArea       =  binf.ext.initAddressArea;
	binf.initAddressInput      =  binf.ext.initAddressInput;
	binf.getRegionElementHTML  =  binf.ext.getRegionElementHTML;
	binf.region = binf.ext.region;
	window.binf = binf;
	
})(jQuery);








