//== Class definition

var DatatableHtmlTableDemo = function () {
	//== Private functions

	// demo initializer
	var demo = function () {

		var datatable = $('.m-datatable').mDatatable({
			search: {
				input: $('#generalSearch')
			},
			layout: {
				scroll: true,
				height: 400
			},
			columns: [
				{
					field: "Deposit Paid",
					type: "number",
					locked: {left: "xl"}
				},
				{
					field: "Order Date",
					type: "date",
					format: "YYYY-MM-DD",
					locked: {left: "xl"}
				}
			]
		});
	};

	return {
		//== Public functions
		init: function () {
			// init dmeo
			demo();
		}
	};
}();

jQuery(document).ready(function () {
	DatatableHtmlTableDemo.init();
});