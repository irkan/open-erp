var KTBootstrapDatetimepicker = function () {
    var demos = function () {
        $('.datetimepicker-element').datetimepicker({
            todayHighlight: true,
            autoclose: true,
            format: 'dd.mm.yyyy hh:ii:ss'
        });
    }

    return {
        init: function() {
            demos(); 
        }
    };
}();

jQuery(document).ready(function() {
    KTBootstrapDatetimepicker.init();
});