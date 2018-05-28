//== Class definition

var DatatableColumnRenderingDemo = function() {
  //== Private functions

  // basic demo
  var demo = function() {

    var datatable = $('.m_datatable').mDatatable({
      // datasource definition
      data: {
        type: 'remote',
        source: {
          read: {
            url: 'inc/api/datatables/demos/default.php',
          },
        },
        pageSize: 10, // display 20 records per page
        serverPaging: true,
        serverFiltering: true,
        serverSorting: true,
      },

      // layout definition
      layout: {
        theme: 'default', // datatable theme
        class: '', // custom wrapper class
        scroll: false, // enable/disable datatable scroll both horizontal and vertical when needed.
        footer: false // display/hide footer
      },

      // column sorting
      sortable: true,

      pagination: true,

      search: {
        input: $('#generalSearch'),
        delay: 400,
      },

      rows: {
        callback: function(row, data, index) {
        },
      },

      // columns definition
      columns: [
        {
          field: 'RecordID',
          title: '#',
          sortable: false, // disable sort for this column
          width: 40,
          textAlign: 'center',
          selector: {class: 'm-checkbox--solid m-checkbox--brand'},
        }, {
          width: 200,
          field: 'CompanyAgent',
          title: 'Agent',
          template: function(data) {
            var number = mUtil.getRandomInt(1, 14);
            var user_img = '100_' + number + '.jpg';

            if (number > 8) {
              output = '<div class="m-card-user m-card-user--sm">\
								<div class="m-card-user__pic">\
									<img src="./assets/app/media/img/users/' + user_img + '" class="m--img-rounded m--marginless" alt="photo">\
								</div>\
								<div class="m-card-user__details">\
									<span class="m-card-user__name">' + data.CompanyAgent + '</span>\
									<a href="" class="m-card-user__email m-link">' +
                  data.CompanyName + '</a>\
								</div>\
							</div>';
            } else {
              var stateNo = mUtil.getRandomInt(0, 7);
              var states = [
                'success',
                'brand',
                'danger',
                'accent',
                'warning',
                'metal',
                'primary',
                'info'];
              var state = states[stateNo];

              output = '<div class="m-card-user m-card-user--sm">\
								<div class="m-card-user__pic">\
									<div class="m-card-user__no-photo m--bg-fill-' + state +
                  '"><span>' + data.CompanyAgent.substring(0, 1) + '</span></div>\
								</div>\
								<div class="m-card-user__details">\
									<span class="m-card-user__name">' + data.CompanyAgent + '</span>\
									<a href="" class="m-card-user__email m-link">' +
                  data.CompanyName + '</a>\
								</div>\
							</div>';
            }

            return output;
          },
        }, {
          field: 'ShipCountry',
          title: 'Ship Country',
          width: 150,
          // callback function support for column rendering
          template: function(data) {
            return data.ShipCountry + ' - ' + data.ShipCity;
          },
        }, {
          field: 'ShipAddress',
          title: 'Ship Address',
          width: 200,
        }, {
          field: 'CompanyEmail',
          title: 'Email',
          width: 150,
          template: function(data) {
            return '<a class="m-link" href="mailto:' + data.CompanyEmail +
                '">' +
                data.CompanyEmail + '</a>';
          },
        }, {
          field: 'Status',
          title: 'Status',
          // callback function support for column rendering
          template: function(data) {
            var status = {
              1: {'title': 'Pending', 'class': 'm-badge--brand'},
              2: {'title': 'Delivered', 'class': ' m-badge--metal'},
              3: {'title': 'Canceled', 'class': ' m-badge--primary'},
              4: {'title': 'Success', 'class': ' m-badge--success'},
              5: {'title': 'Info', 'class': ' m-badge--info'},
              6: {'title': 'Danger', 'class': ' m-badge--danger'},
              7: {'title': 'Warning', 'class': ' m-badge--warning'},
            };
            return '<span class="m-badge ' + status[data.Status].class +
                ' m-badge--wide">' + status[data.Status].title + '</span>';
          },
        }, {
          field: 'Type',
          title: 'Type',
          // callback function support for column rendering
          template: function(data) {
            var status = {
              1: {'title': 'Online', 'state': 'danger'},
              2: {'title': 'Retail', 'state': 'primary'},
              3: {'title': 'Direct', 'state': 'accent'},
            };
            return '<span class="m-badge m-badge--' + status[data.Type].state +
                ' m-badge--dot"></span>&nbsp;<span class="m--font-bold m--font-' +
                status[data.Type].state + '">' + status[data.Type].title +
                '</span>';
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
    });

	  $('#m_form_status').on('change', function() {
		  datatable.search($(this).val(), 'Status');
	  });

	  $('#m_form_type').on('change', function() {
		  datatable.search($(this).val(), 'Type');
	  });

    $('#m_form_status, #m_form_type').selectpicker();
  };

  return {
    // public functions
    init: function() {
      demo();
    },
  };
}();

jQuery(document).ready(function() {
  DatatableColumnRenderingDemo.init();
});