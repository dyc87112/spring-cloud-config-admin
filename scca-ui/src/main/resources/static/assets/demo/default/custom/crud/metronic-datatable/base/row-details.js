//== Class definition

var DatatableAutoColumnHideDemo = function() {
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
        pageSize: 10,
        saveState: false,
        serverPaging: true,
        serverFiltering: true,
        serverSorting: true,
      },

      // column sorting
      sortable: true,

      pagination: true,

      toolbar: {
        // toolbar items
        items: {
          // pagination
          pagination: {
            // page size select
            pageSizeSelect: [10, 20, 30, 50, 100],
          },
        },
      },

      search: {
        input: $('#generalSearch'),
      },

      rows: {
        // auto hide columns, if rows overflow
        autoHide: true,
      },

      // columns definition
      columns: [
        {
          field: 'OrderID',
          title: 'Order ID',
          width: 150,
          template: '{{OrderID}} - {{ShipCountry}}',
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
          sortable: 'asc',
          type: 'date',
          format: 'MM/DD/YYYY',
        }, {
          field: 'Latitude',
          title: 'Latitude',
          type: 'number',
        }, {
          field: 'Longitude',
          title: 'Longitude',
        }, {
          field: 'Notes',
          title: 'Notes',
          width: 350,
        }, {
          field: 'Department',
          title: 'Department',
        }, {
          field: 'Website',
          title: 'Website',
        }, {
          field: 'TotalPayment',
          title: 'Total Payment',
        }, {
          field: 'Status',
          title: 'Status',
          // callback function support for column rendering
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
          // callback function support for column rendering
          template: function(row) {
            var status = {
              1: {'title': 'Online', 'state': 'danger'},
              2: {'title': 'Retail', 'state': 'primary'},
              3: {'title': 'Direct', 'state': 'accent'},
            };
            return '<span class="m-badge m-badge--' + status[row.Type].state + ' m-badge--dot"></span>&nbsp;<span class="m--font-bold m--font-' + status[row.Type].state + '">' +
                status[row.Type].title + '</span>';
          },
        }],
    });

  };

  return {
    // public functions
    init: function() {
      demo();
    },
  };
}();

jQuery(document).ready(function() {
  DatatableAutoColumnHideDemo.init();
});