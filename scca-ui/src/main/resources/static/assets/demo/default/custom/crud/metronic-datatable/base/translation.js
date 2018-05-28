//== Class definition

var DatatableTranslationDemo = function () {
	//== Private functions

	// basic demo
	var demo = function () {

		var datatable = $('.m_datatable').mDatatable({
			// datasource definition
			data: {
				type: 'remote',
				source: {
					read: {
						url: 'inc/api/datatables/demos/default.php'
					}
				},
				pageSize: 10,
				serverPaging: true,
				serverFiltering: false,
				serverSorting: true
			},

			// layout definition
			layout: {
				theme: 'default', // datatable theme
				class: '', // custom wrapper class
				scroll: false, // enable/disable datatable scroll both horizontal and vertical when needed.
				height: null, // datatable's body's fixed height
				footer: false // display/hide footer
			},

			// column sorting
			sortable: true,

			pagination: true,

			search: {
				input: $('#generalSearch')
			},

			// columns definition
			columns: [{
				field: "RecordID",
				title: "#",
				sortable: false, // disable sort for this column
				width: 40,
				selector: {class: 'm-checkbox--solid m-checkbox--brand'}
			}, {
				field: "OrderID",
				title: "Order ID",
				sortable: 'asc', // default sort
				filterable: false, // disable or enable filtering,
				width: 150
			}, {
				field: "ShipCity",
				title: "Ship City"
			}, {
				field: "ShipName",
				title: "Ship Name",
				width: 150
			}, {
				field: "CompanyEmail",
				title: "Email",
				width: 150
			}, {
				field: "CompanyAgent",
				title: "Agent"
			}, {
				field: "Department",
				title: "Department"
			}, {
				field: "ShipDate",
				title: "Ship Date"
			}],

			translate: {
				records: {
					processing: 'Cargando...',
					noRecords: 'No se encontrarón archivos'
				},
				toolbar: {
					pagination: {
						items: {
							default: {
								first: 'Primero',
								prev: 'Anterior',
								next: 'Siguiente',
								last: 'Último',
								more: 'Más páginas',
								input: 'Número de página',
								select: 'Seleccionar tamaño de página'
							},
							info: 'Viendo {{start}} - {{end}} de {{total}} registros'
						}
					}
				}
			}
		});
	};

	return {
		// public functions
		init: function () {
			demo();
		}
	};
}();

jQuery(document).ready(function () {
	DatatableTranslationDemo.init();
});