
;(function() {
	
	var orderStatus = [ {
		name : 'NOT_PAID',
		value : 100,
		text : '未付款'
	}, {
		name : 'PAID',
		value : 110,
		text : '已付款'
	},{
		name : 'REFUND_APPLYING',
		value : 120,
		text : '退款申请中'
	},  {
		name : 'CONFIRMED',
		value : 130,
		text : '已确认'
	}, {
		name : 'DISTRIBUTED',
		value : 140,
		text : '已派发'
	}, {
		name : 'SERVED',
		value : 150,
		text : '已服务'
	}, {
		name : 'EVALUATED',
		value : 160,
		text : '已评价'
	}, {
		name : 'CANCELED',
		value : 170,
		text : '订单取消'
	}, {
		name : 'EXCEPTION',
		value : 180,
		text : '异常关闭'
	}, 
	
	////////////////////////////////////////////////
	/////////////     退款单状态          //////////////
	///////////////////////////////////////////////
//	{
//		name : 'NOT_REFUND',
//		value : 6,
//		text : '未退款'
//	}, 
	{
		name : 'REFUND_APPLYING',
		value : 200,
		text : '退款申请中'
	}, {
		name : 'REFUND_PAUSE',
		value : 210,
		text : '暂停退款'
	}, {
		name : 'REFUND_CONFIRMED',
		value : 220,
		text : '确认退款'
	}, {
		name : 'REFUND_COMPLETE',
		value : 230,
		text : '退款完成'
	}, {
		name : 'REFUND_FAILED',
		value : 240,
		text : '退款失败'		
	} ];

	var payType = {
		
	}
	var typeVar = {
		orderStatus : orderStatus,
		payType : payType
	}
	
	window.typeVar = typeVar; 

})(); 





