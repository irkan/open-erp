// Class definition

var KTBootstrapDatepicker = function () {

    var arrows;
    if (KTUtil.isRTL()) {
        arrows = {
            leftArrow: '<i class="la la-angle-right"></i>',
            rightArrow: '<i class="la la-angle-left"></i>'
        }
    } else {
        arrows = {
            leftArrow: '<i class="la la-angle-left"></i>',
            rightArrow: '<i class="la la-angle-right"></i>'
        }
    }
    var demos = function () {

        $('.datepicker-element').datepicker({
            rtl: KTUtil.isRTL(),
            format: 'dd.mm.yyyy',
            todayBtn: "linked",
            clearBtn: true,
            todayHighlight: true,
            templates: arrows
        });
    };

    return {
        init: function() {
            demos(); 
        }
    };
}();

jQuery(document).ready(function() {    
    KTBootstrapDatepicker.init();
});