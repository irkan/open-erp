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

    var demo5 = function() {
        $("#kt_tree_5").jstree({
            "core" : {
                "themes" : {
                    "responsive": false
                },
                // so that create works
                "check_callback" : true,
                'data': [{
                    "text": "Parent Node",
                    "children": [{
                        "text": "Initially selected",
                        "state": {
                            "selected": true
                        }
                    }, {
                        "text": "Custom Icon",
                        "icon": "fa fa-warning kt-font-danger"
                    }, {
                        "text": "Initially open",
                        "icon" : "fa fa-folder kt-font-success",
                        "state": {
                            "opened": true
                        },
                        "children": [
                            {"text": "Another node", "icon" : "fa fa-file kt-font-waring"}
                        ]
                    }, {
                        "text": "Another Custom Icon",
                        "icon": "fa fa-warning kt-font-waring"
                    }, {
                        "text": "Disabled Node",
                        "icon": "fa fa-check kt-font-success",
                        "state": {
                            "disabled": true
                        }
                    }, {
                        "text": "Sub Nodes",
                        "icon": "fa fa-folder kt-font-danger",
                        "children": [
                            {"text": "Item 1", "icon" : "fa fa-file kt-font-waring"},
                            {"text": "Item 2", "icon" : "fa fa-file kt-font-success"},
                            {"text": "Item 3", "icon" : "fa fa-file kt-font-default"},
                            {"text": "Item 4", "icon" : "fa fa-file kt-font-danger"},
                            {"text": "Item 5", "icon" : "fa fa-file kt-font-info"}
                        ]
                    }]
                },
                    "Another Node"
                ]
            },
            "types" : {
                "default" : {
                    "icon" : "fa fa-folder kt-font-success"
                },
                "file" : {
                    "icon" : "fa fa-file  kt-font-success"
                }
            },
            "state" : { "key" : "demo2" },
            "plugins" : [ "dnd", "state", "types" ]
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