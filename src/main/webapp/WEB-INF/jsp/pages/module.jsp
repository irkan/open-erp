<%--
  Created by IntelliJ IDEA.
  User: irkan.ahmadov
  Date: 01.09.2019
  Time: 1:22
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="uj" uri="/WEB-INF/tld/UtilJson.tld"%>
<%@ taglib prefix="ua" uri="/WEB-INF/tld/UserAccess.tld"%>
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
                    Menyunun yer alacağı kontent: Menyuya baxış
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
                    <div class="form-group">
                        <form:label path="module">Üst modul</form:label>
                        <form:select  path="module" cssClass="custom-select form-control">
                            <form:options items="${list}" itemLabel="name" itemValue="id" />
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
                    </div>
                    <div class="form-group">
                        <form:label path="path">URL Path</form:label>
                        <form:input path="path" type="text" cssClass="form-control" placeholder="Modul path daxil edin" />
                    </div>
                    <div class="form-group">
                        <form:label path="icon">İkon</form:label>
                        <form:input path="icon" type="text" cssClass="form-control" placeholder="İkon adını daxil edin" />
                        <form:errors path="icon" cssClass="alert-danger control-label"/>
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
<%--<c:out value="${uj:toJson(parents)}" />--%>
<script src="<c:url value="/assets/vendors/custom/jstree/jstree.bundle.js" />" type="text/javascript"></script>
<script>
    function show(data){
        let json = data;
        let d = JSON.stringify(json, null, 2);
        alert(data);
        alert(d);
        console.log(d);
    }

    $(function(){
        var KTTreeview = function () {
            var demo5 = function() {
                $("#kt_tree_5").jstree({
                    "core" : {
                        "themes" : {
                            "responsive": false
                        },
                        // so that create works
                        "check_callback" : true,
                        'data': [
                            <c:forEach var="t" items="${parents}" varStatus="loop">
                            {
                                "text": "<c:out value="${t.name}"/>",
                                "icon" : "<c:out value="${t.icon}"/>",
                                "li_attr" : { "unique" : "<c:out value="${t.id}"/>" },
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


</script>

