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
<div class="kt-container  kt-grid__item kt-grid__item--fluid">
    <div class="row">
        <div class="col-lg-12">
            <div class="kt-portlet kt-portlet--mobile">
                <div class="kt-portlet__body">
                    <form:form modelAttribute="form" id="form" method="post" action="/admin/template-module-operation" cssClass="form-group">
                        <div class="row">
                            <div class="col-md-4 offset-md-4">
                                <form:select  path="template" cssClass="custom-select form-control" onchange="getTemplateModuleOperation($(this).val())">
                                    <form:option value="0" label="Şablonu seçin"/>
                                    <form:options items="${templates}" itemLabel="name" itemValue="id"  />
                                </form:select>
                            </div>
                            <div class="col-md-2 offset-md-2 text-right">
                                <a href="#" onclick="save()" class="btn btn-primary">Yadda saxla</a>
                            </div>
                        </div>
                        <hr style="width: 100%"/>
                        <c:choose>
                            <c:when test="${not empty list}">
                                <table class="table table-striped- table-bordered table-hover table-checkable" id="group_table">
                                    <thead>
                                    <tr style="height: 190px;">
                                        <td style="width: 15px;"></td>
                                        <td style="width: 25%;"></td>
                                        <td></td>
                                        <td style="width: 20px"></td>
                                        <c:forEach var="t" items="${operations}" varStatus="loop">
                                            <td nowrap style="vertical-align: bottom;">
                                                <div class="rotate" style="width: 30px;"><c:out value="${t.name}" /></div><br/><i class="icon-custom <c:out value="${t.icon}" />"></i>
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="m" items="${modules}" varStatus="loop">
                                        <c:choose>
                                            <c:when test="${not empty m.module}">
                                                <tr>
                                                    <td>${loop.index + 1}</td>
                                                    <td class="text-right"><div style="width: 100%; min-width: 150px;"><span><c:out value="${m.name}" /></span><i class="icon-custom <c:out value="${m.icon}" />"></i></div></td>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${empty m.module.module}">
                                                                <c:out value="${m.module.name}" />
                                                            </c:when>
                                                            <c:otherwise>
                                                                <c:out value="${m.module.module.name}" />
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                    <td class="text-center" style="width: 8px; background-color: #f7f8fa">
                                                        <label class="kt-checkbox kt-checkbox--brand">
                                                            <input type="checkbox" onclick="checkedRow(this)">
                                                            <span style="top: -11px; left: 7px;"></span>
                                                        </label></td>
                                                    <c:forEach var="o" items="${operations}" varStatus="loop">
                                                        <td class="text-center">
                                                            <c:set var="status" value="${utl:checkAccess(list, user_module_operations, template_module_operations, m.id, o.id)}"/>
                                                            <c:choose>
                                                                <c:when test="${status.object ne 0 && not empty status.object}">
                                                                    <c:choose>
                                                                        <c:when test="${status.status}">
                                                                            <label class="kt-checkbox kt-checkbox--brand">
                                                                                <form:checkbox path="moduleOperations" value="${status.object}" checked="checked"/>
                                                                                <span style="top: -11px; left: 4px"></span>
                                                                            </label>
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                            <label class="kt-checkbox kt-checkbox--brand">
                                                                                <form:checkbox path="moduleOperations" value="${status.object}"/>
                                                                                <span style="top: -11px; left: 4px"></span>
                                                                            </label>
                                                                        </c:otherwise>
                                                                    </c:choose>
                                                                </c:when>
                                                            </c:choose>
                                                        </td>
                                                    </c:forEach>
                                                </tr>
                                            </c:when>
                                        </c:choose>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </c:when>
                            <c:otherwise>
                                <div class="row">
                                <div class="col-md-12 text-center" style="letter-spacing: 10px;">
                                    Məlumat yoxdur
                                </div>
                            </div>
                            </c:otherwise>
                        </c:choose>
                    </form:form>
                </div>
            </div>
        </div>

    </div>
</div>

<script src="<c:url value="/assets/js/demo4/pages/crud/datatables/advanced/row-grouping.js" />" type="text/javascript"></script>

<script>
    function checkedRow(element){
        var row = $(element).closest("tr");
        if($(element).prop("checked") == true){
            $(row).find('input[type="checkbox"]').each(function(i, chk) {
                $(chk).prop('checked', true);
                $(chk).addClass(":after");
            });
        } else {
            $(row).find('input[type="checkbox"]').each(function(i, chk) {
                $(chk).prop('checked', false);
                $(chk).removeClass(":after");
            });
        }
    }

    function getTemplateModuleOperation(id){
        swal.fire({
            text: 'Proses davam edir...',
            allowOutsideClick: false,
            onOpen: function() {
                swal.showLoading();
                $.ajax({
                    url: '/admin/get-template-module-operation/' + id,
                    type: 'GET',
                    dataType: 'json',
                    beforeSend: function() {
                        $('input[type=checkbox]').prop('checked', false);
                    },
                    success: function(data) {
                        console.log(data);
                        $.each(data, function(k, v) {
                            $('input[type=checkbox][value="'+v.moduleOperation.id+'"]').prop('checked', true);
                            $('input[type=checkbox][value="'+v.moduleOperation.id+'"]').parent().find("span").addClass(":after");
                            console.log(k + ' - ' + v)
                            console.log(v.moduleOperation.id)
                        });
                        swal.close();
                    },
                    error: function() {
                        swal.fire({
                            title: "Xəta baş verdi!",
                            html: "Məlumat tapılmadı!",
                            type: "error",
                            cancelButtonText: 'Bağla',
                            cancelButtonColor: '#c40000',
                            cancelButtonClass: 'btn btn-danger',
                            footer: '<a href>Məlumatlar yenilənsinmi?</a>'
                        });
                    },
                    complete: function(){
                    }
                })
            }
        });
    }

    function save() {
        if(parseInt($("#template").val())>0){
            submit($("#form"));
        } else {
            swal.fire({
                title: "Xəta baş verdi!",
                html: "Şablon seçilməyib!",
                type: "error",
                cancelButtonText: 'Bağla',
                cancelButtonColor: '#c40000',
                cancelButtonClass: 'btn btn-danger',
                footer: '<a href>Məlumatlar yenilənsinmi?</a>'
            });
        }
    }

</script>