//== Class definition

var DatatableRecordSelectionDemo = function() {
	//== Private functions

	var options = {
		// datasource definition
		data: {
			type: 'remote',
			source: {
				read: {
					url: 'inc/api/datatables/demos/default.php',
				},
			},
			pageSize: 10,
			serverPaging: true,
			serverFiltering: true,
			serverSorting: true,
		},

		// layout definition
		layout: {
			theme: 'default', // datatable theme
			class: '', // custom wrapper class
			scroll: true, // enable/disable datatable scroll both horizontal and vertical when needed.
			height: 550, // datatable's body's fixed height
			footer: false // display/hide footer
		},

		// column sorting
		sortable: true,

		pagination: true,

		// columns definition
		columns: [
			{
				field: 'RecordID',
				title: '#',
				sortable: false,
				width: 40,
				textAlign: 'center',
				selector: {class: 'm-checkbox--solid m-checkbox--brand'},
			}, {
				field: 'ID',
				title: 'ID',
				width: 40,
				template: '{{RecordID}}',
			}, {
				field: 'ShipCountry',
				title: 'Ship Country',
				width: 150,
				template: function(row) {
					// callback function support for column rendering
					return row.ShipCountry + ' - ' + row.ShipCity;
				},
			}, {
				field: 'ShipCity',
				title: 'Ship City',
			}, {
				field: 'Currency',
				title: 'Currency',
				width: 100,
			}, {
				field: 'ShipDate',
				title: 'Ship Date',
			}, {
				field: 'Latitude',
				title: 'Latitude',
			}, {
				field: 'Status',
				title: 'Status',
				template: function(row) {
					var status = {
						1: {'title': 'Pending', 'class': 'm-badge--brand'},
						2: {'title': 'Delivered', 'class': ' m-badge--metal'},
						3: {'title': 'Canceled', 'class': ' m-badge--primary'},
						4: {'title': 'Success', 'class': ' m-badge--success'},
						5: {'title': 'Info', 'class': ' m-badge--info'},
						6: {'title': 'Danger', 'class': ' m-badge--danger'},
						7: {'title': 'Warning', 'class': ' m-badge--warning'},
					};
					return '<span class="m-badge ' + status[row.Status].class + ' m-badge--wide">' + status[row.Status].title + '</span>';
				},
			}, {
				field: 'Type',
				title: 'Type',
				template: function(row) {
					var status = {
						1: {'title': 'Online', 'state': 'danger'},
						2: {'title': 'Retail', 'state': 'primary'},
						3: {'title': 'Direct', 'state': 'accent'},
					};
					return '<span class="m-badge m-badge--' + status[row.Type].state + ' m-badge--dot"></span>&nbsp;<span class="m--font-bold m--font-' + status[row.Type].state +
						'">' +
						status[row.Type].title + '</span>';
				},
			}, {
				field: 'Actions',
				width: 110,
				title: 'Actions',
				sortable: false,
				overflow: 'visible',
				template: function (row, index, datatable) {
					var dropup = (datatable.getPageSize() - index) <= 4 ? 'dropup' : '';
					return '\
						<div class="dropdown ' + dropup + '">\
							<a href="#" class="btn m-btn m-btn--hover-accent m-btn--icon m-btn--icon-only m-btn--pill" data-toggle="dropdown">\
                                <i class="la la-ellipsis-h"></i>\
                            </a>\
						  	<div class="dropdown-menu dropdown-menu-right">\
						    	<a class="dropdown-item" href="#"><i class="la la-edit"></i> Edit Details</a>\
						    	<a class="dropdown-item" href="#"><i class="la la-leaf"></i> Update Status</a>\
						    	<a class="dropdown-item" href="#"><i class="la la-print"></i> Generate Report</a>\
						  	</div>\
						</div>\
						<a href="#" class="m-portlet__nav-link btn m-btn m-btn--hover-accent m-btn--icon m-btn--icon-only m-btn--pill" title="Edit details">\
							<i class="la la-edit"></i>\
						</a>\
						<a href="#" class="m-portlet__nav-link btn m-btn m-btn--hover-danger m-btn--icon m-btn--icon-only m-btn--pill" title="Delete">\
							<i class="la la-trash"></i>\
						</a>\
					';
				},
			}],
	};

	// basic demo
	var localSelectorDemo = function() {

		options.search = {
			input: $('#generalSearch'),
		};

		var datatable = $('#local_record_selection').mDatatable(options);

		$('#m_form_status').on('change', function() {
			datatable.search($(this).val().toLowerCase(), 'Status');
		});

		$('#m_form_type').on('change', function() {
			datatable.search($(this).val().toLowerCase(), 'Type');
		});

		$('#m_form_status,#m_form_type').selectpicker();

		datatable.on('m-datatable--on-check m-datatable--on-uncheck m-datatable--on-layout-updated', function(e) {
			var checkedNodes = datatable.rows('.m-datatable__row--active').nodes();
			var count = checkedNodes.length;
			$('#m_datatable_selected_number').html(count);
			if (count > 0) {
				$('#m_datatable_group_action_form').collapse('show');
			} else {
				$('#m_datatable_group_action_form').collapse('hide');
			}
		});

		$('#m_modal_fetch_id').on('show.bs.modal', function(e) {
			var ids = datatable.rows('.m-datatable__row--active').
				nodes().
				find('.m-checkbox--single > [type="checkbox"]').
				map(function(i, chk) {
					return $(chk).val();
				});
			var c = document.createDocumentFragment();
			for (var i = 0; i < ids.length; i++) {
				var li = document.createElement('li');
				li.setAttribute('data-id', ids[i]);
				li.innerHTML = 'Selected record ID: ' + ids[i];
				c.appendChild(li);
			}
			$(e.target).find('.m_datatable_selected_ids').append(c);
		}).on('hide.bs.modal', function(e) {
			$(e.target).find('.m_datatable_selected_ids').empty();
		});

	};

	var serverSelectorDemo = function() {

		// enable extension
		options.extensions = {
			checkbox: {},
		};
		options.search = {
			input: $('#generalSearch1'),
		};

		var datatable = $('#server_record_selection').mDatatable(options);

		$('#m_form_status1').on('change', function() {
			datatable.search($(this).val().toLowerCase(), 'Status');
		});

		$('#m_form_type1').on('change', function() {
			datatable.search($(this).val().toLowerCase(), 'Type');
		});

		$('#m_form_status1,#m_form_type1').selectpicker();

		datatable.on('m-datatable--on-click-checkbox m-datatable--on-layout-updated', function(e) {
			// datatable.checkbox() access to extension methods
			var ids = datatable.checkbox().getSelectedId();
			var count = ids.length;
			$('#m_datatable_selected_number1').html(count);
			if (count > 0) {
				$('#m_datatable_group_action_form1').collapse('show');
			} else {
				$('#m_datatable_group_action_form1').collapse('hide');
			}
		});

		$('#m_modal_fetch_id_server').on('show.bs.modal', function(e) {
			var ids = datatable.checkbox().getSelectedId();
			var c = document.createDocumentFragment();
			for (var i = 0; i < ids.length; i++) {
				var li = document.createElement('li');
				li.setAttribute('data-id', ids[i]);
				li.innerHTML = 'Selected record ID: ' + ids[i];
				c.appendChild(li);
			}
			$(e.target).find('.m_datatable_selected_ids').append(c);
		}).on('hide.bs.modal', function(e) {
			$(e.target).find('.m_datatable_selected_ids').empty();
		});

	};

	return {
		// public functions
		init: function() {
			localSelectorDemo();
			serverSelectorDemo();
		},
	};
}();

jQuery(document).ready(function() {
	DatatableRecordSelectionDemo.init();
});