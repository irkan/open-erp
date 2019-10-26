"use strict";
var KTDatatablesAdvancedRowGrouping = function() {

	var initTable1 = function() {
		var table = $('#group_table');

		table.DataTable({
			responsive: true,
			pageLength: 100,
			order: [[2, 'asc']],
			drawCallback: function(settings) {
				var api = this.api();
				var rows = api.rows({page: 'current'}).nodes();
				var last = null;

				api.column(2, {page: 'current'}).data().each(function(group, i) {
					if (last !== group) {
						$(rows).eq(i).before(
							'<tr class="group"><td colspan="30">' + group + '</td></tr>'
						);
						last = group;
					}
				});
			},
			columnDefs: [
				{
					targets: [2],
					visible: false
				}
			]
		});
	};

	return {
		init: function() {
			initTable1();
		}
	};
}();

jQuery(document).ready(function() {
	KTDatatablesAdvancedRowGrouping.init();
});