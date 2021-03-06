<%--
  Created by IntelliJ IDEA.
  User: irkan.ahmadov
  Date: 01.09.2019
  Time: 1:22
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="utl" uri="/WEB-INF/tld/Util.tld"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link href="<c:url value="/assets/vendors/custom/jstree/jstree.bundle.css" />" rel="stylesheet" type="text/css" />
<div class="kt-container  kt-grid__item kt-grid__item--fluid">
    <div class="row">

        <div class="col-lg-6">
            <div class="kt-portlet kt-portlet--mobile">
                <div class="kt-portlet__body">
                    <div id="kt_tree_5" class="tree-demo">
                    </div>
                </div>
            </div>
        </div>
        <div class="col-lg-6">
            <div class="kt-portlet kt-portlet--mobile">
                <div class="kt-portlet__body">
                    <div id="module-view-content">
                        Heç bir menyu seçilməyib...
                    </div>
                </div>
            </div>
        </div>

    </div>
</div>

<div class="modal fade" id="modal-operation" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title"></h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form:form modelAttribute="form" id="form" method="post" action="/admin/module" cssClass="form-group">
                    <form:hidden path="id"/>
                    <div class="form-group">
                        <form:label path="module.id">Üst modul</form:label>
                        <form:select  path="module.id" cssClass="custom-select form-control">
                            <form:option value=""></form:option>
                            <form:options items="${parents}" itemLabel="name" itemValue="id" />
                        </form:select>
                    </div>
                    <div class="form-group">
                        <form:label path="name">Ad</form:label>
                        <form:input path="name" type="text" cssClass="form-control" placeholder="Modulun adını daxil edin" />
                        <form:errors path="name" cssClass="alert-danger control-label"/>
                    </div>
                    <div class="form-group">
                        <form:label path="description">Açıqlama</form:label>
                        <form:input path="description" type="text" cssClass="form-control" placeholder="Modul açıqlamasını daxil edin" />
                        <form:errors path="description" cssClass="alert-danger control-label"/>
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <form:label path="path">URL Path</form:label>
                                <form:input path="path" type="text" cssClass="form-control" placeholder="Modul path daxil edin" />
                                <form:errors path="path" cssClass="alert-danger control-label"/>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <form:label path="icon">İkon</form:label>
                                <form:input path="icon" type="text" cssClass="form-control" placeholder="İkon adını daxil edin" />
                                <form:errors path="icon" cssClass="alert-danger control-label"/>
                                <div class="text-right" style="width: 100%">
                                    <a href="/route/sub/admin/flat-icon" target="_blank" class="kt-link kt-font-sm kt-font-bold kt-margin-t-5">Flat ikonlardan ikon seçin</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </form:form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="submit($('#form'));">Yadda saxla</button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Bağla</button>
            </div>
        </div>
    </div>
</div>

<form style="display: none" action="/admin/module/move" method="post" id="module-move-form">
    <input type="hidden" name="parent-id" id="parent-id">
    <input type="hidden" name="sub-id" id="sub-id">
</form>
<%--<c:out value="${utl:toJson(parents)}" />--%>
<script src="<c:url value="/assets/vendors/custom/jstree/jstree.bundle.js" />" type="text/javascript"></script>
<script>
    $(function(){
        var KTTreeview = function () {
            var demo5 = function() {
                $("#kt_tree_5").jstree({
                    "core" : {
                        "themes" : {
                            "responsive": false
                        },
                        "check_callback" : true,
                        'data': [
                            <c:set var="edit" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'edit')}"/>
                            <c:set var="delete" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'delete')}"/>
                            <c:forEach var="t" items="${parents}" varStatus="loop">
                            {
                                "text": "<c:out value="${t.name}"/>",
                                "icon" : "<c:out value="${t.icon}"/>",
                                "li_attr" : { "unique" : "<c:out value="${t.id}"/>" },
                                "a_attr" : { "href" : "javascript:;", "onclick" : "showModule('<c:out value="${utl:toJson(t)}" />', '<c:out value="${edit.status}" />', '<c:out value="${edit.object.icon}" />', '<c:out value="${delete.status}" />', '<c:out value="${delete.object.icon}" />');" },
                                "state": {
                                    "opened": true
                                }
                                <c:if test="${t.children.size()>0}">
                                ,"children": [
                                    <c:forEach var="p" items="${t.children}" varStatus="loop">
                                    {
                                        "text": "<c:out value="${p.name}"/>",
                                        "icon" : "<c:out value="${p.icon}"/>",
                                        "li_attr" : { "unique" : "<c:out value="${p.id}"/>" },
                                        "a_attr" : { "href" : "javascript:;", "onclick" : "showModule('<c:out value="${utl:toJson(p)}" />', '<c:out value="${edit.status}" />', '<c:out value="${edit.object.icon}" />', '<c:out value="${delete.status}" />', '<c:out value="${delete.object.icon}" />');" },
                                        "state": {
                                            "opened": true
                                        }
                                        <c:if test="${p.children.size()>0}">
                                        ,"children": [
                                            <c:forEach var="f" items="${p.children}" varStatus="loop">
                                            {
                                                "text": "<c:out value="${f.name}"/>",
                                                "icon" : "<c:out value="${f.icon}"/>",
                                                "li_attr" : { "unique" : "<c:out value="${f.id}"/>" },
                                                "a_attr" : { "href" : "javascript:;", "onclick" : "showModule('<c:out value="${utl:toJson(f)}" />', '<c:out value="${edit.status}" />', '<c:out value="${edit.object.icon}" />', '<c:out value="${delete.status}" />', '<c:out value="${delete.object.icon}" />');" },
                                                "state": {
                                                    "opened": true
                                                }
                                                <c:if test="${f.children.size()>0}">
                                                ,"children": [
                                                    <c:forEach var="m" items="${f.children}" varStatus="loop">
                                                    {
                                                        "text": "<c:out value="${m.name}"/>",
                                                        "icon" : "<c:out value="${m.icon}"/>",
                                                        "li_attr" : { "unique" : "<c:out value="${m.id}"/>" },
                                                        "a_attr" : { "href" : "javascript:;", "onclick" : "showModule('<c:out value="${utl:toJson(m)}" />', '<c:out value="${edit.status}" />', '<c:out value="${edit.object.icon}" />', '<c:out value="${delete.status}" />', '<c:out value="${delete.object.icon}" />');" },
                                                        "state": {
                                                            "opened": true
                                                        }
                                                        <c:if test="${m.children.size()>0}">
                                                        ,"children": [
                                                            <c:forEach var="n" items="${m.children}" varStatus="loop">
                                                            {
                                                                "text": "<c:out value="${n.name}"/>",
                                                                "icon" : "<c:out value="${n.icon}"/>",
                                                                "li_attr" : { "unique" : "<c:out value="${n.id}"/>" },
                                                                "a_attr" : { "href" : "javascript:;", "onclick" : "showModule('<c:out value="${utl:toJson(n)}" />', '<c:out value="${edit.status}" />', '<c:out value="${edit.object.icon}" />', '<c:out value="${delete.status}" />', '<c:out value="${delete.object.icon}" />');" },
                                                                "state": {
                                                                    "opened": true
                                                                }
                                                                <c:if test="${n.children.size()>0}">
                                                                ,"children": [
                                                                    <c:forEach var="o" items="${n.children}" varStatus="loop">
                                                                    {
                                                                        "text": "<c:out value="${o.name}"/>",
                                                                        "icon" : "<c:out value="${o.icon}"/>",
                                                                        "li_attr" : { "unique" : "<c:out value="${o.id}"/>" },
                                                                        "a_attr" : { "href" : "javascript:;", "onclick" : "showModule('<c:out value="${utl:toJson(o)}" />', '<c:out value="${edit.status}" />', '<c:out value="${edit.object.icon}" />', '<c:out value="${delete.status}" />', '<c:out value="${delete.object.icon}" />');" },
                                                                        "state": {
                                                                            "opened": true
                                                                        }
                                                                    },
                                                                    </c:forEach>
                                                                ]
                                                                </c:if>
                                                            },
                                                            </c:forEach>
                                                        ]
                                                        </c:if>
                                                    },
                                                    </c:forEach>
                                                ]
                                                </c:if>
                                            },
                                            </c:forEach>
                                        ]
                                        </c:if>
                                    },
                                    </c:forEach>
                                ]
                                </c:if>
                            },
                            </c:forEach>
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

                $('#kt_tree_5').on("move_node.jstree", function (e, data) {
                    $("#sub-id").val($("#"+data.parent).attr("unique"));
                    $("#parent-id").val($("#"+data.node.id).attr("unique"));
                    submit($("#module-move-form"));
                });
            };
            return {
                init: function () {
                    demo5();
                }
            };
        }();

        KTTreeview.init();
    })

    function showModule(data, edit_status, edit_icon, delete_status, delete_icon){
        let obj = jQuery.parseJSON(data.replace(/\&#034;/g, '"'));
        let content = '<div class="row">' +
        '<div class="col-sm-2">' +
        '<i class="'+obj.icon+'" style="font-size: 48px;"></i>' +
        '</div>' +
        '<div class="col-sm-6">' +
            '<label class="view-label-1">'+obj.name+'</label><br/>' +
            '<label class="view-label-2">'+obj.description+'</label><br/>' +
            '<label class="view-label-2">'+obj.path+'</label><br/>' +
            '<label class="view-label-2">'+obj.icon+'</label>' +
        '</div>';

        if(edit_status){
            content+='<div class="col-sm-2 text-center">'+
                '<a href="javascript:edit($(\'#form\'), \''+data+'\', \'modal-operation\', \'Redaktə\')"  class="btn btn-sm btn-clean btn-icon btn-icon-md" >' +
                '<i class="'+edit_icon+'" style="font-size: 24px;"></i>' +
                '</a> ' +
                '</div>';
        }

        if(delete_status){
            content+='<div class="col-sm-2 text-center">' +
            '<a href="javascript:deleteData('+obj.id+', \''+obj.name+'\')"  class="btn btn-sm btn-clean btn-icon btn-icon-md" >' +
            '<i class="'+delete_icon+'" style="font-size: 24px;"></i>' +
            '</a> ' +
            '</div>'
        }

        content+='</div>';

        $("#module-view-content").html(content);
    }

    $( "#form" ).validate({
        rules: {
            "module.id": {
                required: true
            },
            name: {
                required: true
            },
            path: {
                required: true
            }
        },
        invalidHandler: function(event, validator) {
                    KTUtil.scrollTop();
            swal.close();
        },
    })
</script>