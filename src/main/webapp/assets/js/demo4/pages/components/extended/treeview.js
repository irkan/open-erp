"use strict";

var KTTreeview = function () {
    var demo2 = function () {
        $('#kt_tree_2').jstree({
            "core" : {
                "themes" : {
                    "responsive": false
                }            
            },
            "types" : {
                "default" : {
                    "icon" : "fa fa-folder kt-font-warning"
                },
                "file" : {
                    "icon" : "fa fa-file  kt-font-warning"
                }
            },
            "plugins": ["types"]
        });

        // handle link clicks in tree nodes(support target="_blank" as well)
        $('#kt_tree_2').on('select_node.jstree', function(e,data) { 
            var link = $('#' + data.selected).find('a');
            if (link.attr("href") != "#" && link.attr("href") != "javascript:;" && link.attr("href") != "") {
                if (link.attr("target") == "_blank") {
                    link.attr("href").target = "_blank";
                }
                document.location.href = link.attr("href");
                return false;
            }
        });
    }
    return {
        init: function () {
            demo2();
        }
    };
}();

jQuery(document).ready(function() {    
    KTTreeview.init();
});