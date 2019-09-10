"use strict";
var KTDatatablesBasicBasic = function() {

	var initTable1 = function() {
		var table = $('#kt_table_1');

		table.DataTable({
			responsive: true,

			// DOM Layout settings
			dom: "<'row'<'col-sm-12'tr>><'row'<'col-sm-12 col-md-5'i><'col-sm-12 col-md-7 dataTables_pager'lp>>",

			lengthMenu: [5, 10, 25, 50],

			pageLength: 10,

			language: {
				'lengthMenu': 'Display _MENU_',
			},
			order: [[1, 'desc']],
			columnDefs: [
				{
					targets: 0,
					width: '30px',
					className: 'dt-center',
					orderable: false
				},
			],
		});

		table.on('change', '.kt-group-checkable', function() {
			var set = $(this).closest('table').find('td:first-child .kt-checkable');
			var checked = $(this).is(':checked');

			$(set).each(function() {
				if (checked) {
					$(this).prop('checked', true);
					$(this).closest('tr').addClass('active');
				}
				else {
					$(this).prop('checked', false);
					$(this).closest('tr').removeClass('active');
				}
			});
		});

		table.on('change', 'tbody tr .kt-checkbox', function() {
			$(this).parents('tr').toggleClass('active');
		});
	};

	return {
		init: function() {
			initTable1();
		},

	};

}();

var KTDatatablesBasicBasic2 = function() {

	var initTable1 = function() {
		var table = $('#kt_table_2');

		table.DataTable({
			responsive: true,

			// DOM Layout settings
			dom: "<'row'<'col-sm-12'tr>><'row'<'col-sm-12 col-md-5'i><'col-sm-12 col-md-7 dataTables_pager'lp>>",

			lengthMenu: [5, 10, 25, 50],

			pageLength: 10,

			language: {
				'lengthMenu': 'Display _MENU_',
			},
			order: [[1, 'desc']],
			columnDefs: [
				{
					targets: 0,
					width: '30px',
					className: 'dt-center',
					orderable: false
				},
			],
		});

		table.on('change', '.kt-group-checkable', function() {
			var set = $(this).closest('table').find('td:first-child .kt-checkable');
			var checked = $(this).is(':checked');

			$(set).each(function() {
				if (checked) {
					$(this).prop('checked', true);
					$(this).closest('tr').addClass('active');
				}
				else {
					$(this).prop('checked', false);
					$(this).closest('tr').removeClass('active');
				}
			});
		});

		table.on('change', 'tbody tr .kt-checkbox', function() {
			$(this).parents('tr').toggleClass('active');
		});
	};

	return {
		init: function() {
			initTable1();
		},

	};

}();


jQuery(document).ready(function() {
	KTDatatablesBasicBasic.init();
	KTDatatablesBasicBasic2.init();
});