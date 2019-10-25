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
<%@ taglib prefix="utl" uri="/WEB-INF/tld/Util.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="kt-container  kt-grid__item kt-grid__item--fluid">
    <div class="alert alert-light alert-elevate" role="alert">
        <div class="alert-icon"><i class="flaticon-warning kt-font-brand"></i></div>
        <div class="alert-text">
            Əmək haqqı hesablamalı ayda bir dəfə olmaqla hər ayın sonu həyata keçirilməlidir! Hesablama əməliyyatının ağırlığını nəzərə alaraq bildirirk ki, göstərilmiş tarix üzrə hesablanmış əmək haqqı üçün təkrar sorğu göndərildikdə, nəticələr tarixçədən təqdim ediləcəkdir!
        </div>
        <div class="alert-close">
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true"><i class="la la-close"></i></span>
            </button>
        </div>
    </div>
    <div class="row">
        <div class="col-lg-12">
            <div class="kt-portlet kt-portlet--mobile">
                <form:form modelAttribute="form" id="form" method="post" action="/payroll/salary/calculate" cssClass="form-group">
                    <input name="id" type="hidden" value="<c:out value="${form.id}"/>" />
                    <input name="workingHourRecord.id" type="hidden" value="<c:out value="${form.workingHourRecord.id}"/>" />
                <c:set var="calculate" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'calculate')}"/>
                <c:choose>
                    <c:when test="${calculate.status}">
                        <div class="kt-portlet__head kt-portlet__head--lg">
                            <div class="kt-portlet__head-title" style="width: 100%">
                                <div class="row">
                                    <div class="col-sm-3">
                                        <div class="form-group">
                                            <form:label path="workingHourRecord.branch">&nbsp;</form:label>
                                            <form:select  path="workingHourRecord.branch" cssClass="custom-select form-control">
                                                <c:forEach var="t" items="${branches}" varStatus="loop">
                                                    <c:set var="selected" value="${t.id==form.workingHourRecord.branch.id?'selected':''}"/>
                                                    <option value="<c:out value="${t.id}"/>" <c:out value="${selected}"/>><c:out value="${t.name}"/></option>
                                                </c:forEach>
                                            </form:select>
                                            <form:errors path="workingHourRecord.branch" cssClass="control-label alert alert-danger" />
                                        </div>
                                    </div>
                                    <div class="col-sm-2">
                                        <div class="form-group">
                                            <label>&nbsp;</label>
                                            <input name="workingHourRecord.monthYear" class="form-control" type="month" value="<c:out value="${form.workingHourRecord.monthYear}"/>" />
                                        </div>
                                    </div>
                                    <div class="col-sm-3">
                                        <label>&nbsp;</label>
                                        <div class="form-group">
                                            <a href="#" onclick="submit($('#form'))" class="btn btn-brand btn-elevate btn-icon-sm" title="<c:out value="${calculate.object.name}"/>">
                                                <i class="la <c:out value="${calculate.object.icon}"/>"></i>
                                                <c:out value="${calculate.object.name}"/>
                                            </a>
                                            <button type="reset" class="btn btn-secondary btn-secondary--icon">
                                                <i class="la la-close"></i>
                                                Təmizlə
                                            </button>
                                        </div>
                                    </div>
                                    <div class="col-sm-4 text-right">
                                        <label>&nbsp;</label>
                                        <div class="form-group">
                                            <c:set var="save" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'save')}"/>
                                            <c:choose>
                                                <c:when test="${save.status}">
                                                    <c:if test="${not empty form.salaryEmployees}">
                                                        <a href="#" onclick="saveWHR($('#form'))" class="btn btn-warning btn-elevate btn-icon-sm" title="<c:out value="${save.object.name}"/>">
                                                            <i class="la <c:out value="${save.object.icon}"/>"></i>
                                                            <c:out value="${save.object.name}"/>
                                                        </a>
                                                    </c:if>
                                                </c:when>
                                            </c:choose>
                                            <c:set var="approve" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'approve')}"/>
                                            <c:choose>
                                                <c:when test="${approve.status}">
                                                    <c:if test="${not empty form and !form.approve}">
                                                        <a href="#" onclick="saveWHR($('#form'))" class="btn btn-success btn-elevate btn-icon-sm" title="<c:out value="${approve.object.name}"/>">
                                                            <i class="la <c:out value="${approve.object.icon}"/>"></i>
                                                            <c:out value="${approve.object.name}"/>
                                                        </a>
                                                    </c:if>
                                                </c:when>
                                            </c:choose>
                                            <c:set var="cancel" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'cancel')}"/>
                                            <c:choose>
                                                <c:when test="${cancel.status}">
                                                    <c:if test="${not empty form and !form.approve}">
                                                        <a href="#" onclick="saveWHR($('#form'))" class="btn btn-dark btn-elevate btn-icon-sm" title="<c:out value="${cancel.object.name}"/>">
                                                            <i class="la <c:out value="${cancel.object.icon}"/>"></i>
                                                            <c:out value="${cancel.object.name}"/>
                                                        </a>
                                                    </c:if>
                                                </c:when>
                                            </c:choose>
                                            <c:set var="delete" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'delete')}"/>
                                            <c:choose>
                                                <c:when test="${delete.status}">
                                                    <c:if test="${not empty form.salaryEmployees}">
                                                        <a href="javascript:deleteData('<c:out value="${form.workingHourRecord.id}" />', '<c:out value="${form.workingHourRecord.month}"/>.<c:out value="${form.workingHourRecord.year}"/> tarixli maaş hesablanması ');" class="btn btn-danger btn-elevate btn-icon-sm" title="<c:out value="${delete.object.name}"/>">
                                                            <i class="la <c:out value="${delete.object.icon}"/>"></i>
                                                            <c:out value="${delete.object.name}"/>
                                                        </a>
                                                    </c:if>
                                                </c:when>
                                            </c:choose>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:when>
                </c:choose>
                <div class="kt-portlet__body">
                    <c:choose>
                        <c:when test="${not empty form.salaryEmployees}">
                            <table class="table table-striped- table-bordered table-hover table-checkable" id="kt_table_1">
                                <thead>
                                <tr>
                                    <th>WHR_ID</th>
                                    <th>ID</th>
                                    <th>Ad Soyad Ata adı</th>
                                    <th>Ümumi əmək haqqı (Rəsmi hissə)</th>
                                    <th>Hesablanmış əmək haqqı (Rəsmi hissə)</th>
                                    <th>Ümumi əmək haqqı</th>
                                    <th>Hesablanmış əmək haqqı (Qeyri rəsmi hissə)</th>
                                    <th>Əməliyyat</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="t" items="${form.salaryEmployees}" varStatus="loop">
                                    <tr>
                                        <td><c:out value="${t.workingHourRecordEmployee.id}" /></td>
                                        <td><c:out value="${t.id}" /></td>
                                        <td><c:out value="${t.workingHourRecordEmployee.fullName}" /></td>
                                        <td>
                                            <c:forEach var="p" items="${t.salaryEmployeeDetails}" varStatus="loop">
                                                <c:if test="${p.key eq '{gross_salary}'}">
                                                    <c:out value="${p.value}" />
                                                </c:if>
                                            </c:forEach>
                                        </td>
                                        <td>
                                            <c:forEach var="p" items="${t.salaryEmployeeDetails}" varStatus="loop">
                                                <c:if test="${p.key eq '{calculated_gross_salary}'}">
                                                    <c:out value="${p.value}" />
                                                </c:if>
                                            </c:forEach>
                                        </td>
                                        <td>
                                            <c:forEach var="p" items="${t.salaryEmployeeDetails}" varStatus="loop">
                                                <c:if test="${p.key eq '{salary}'}">
                                                    <c:out value="${p.value}" />
                                                </c:if>
                                            </c:forEach>
                                        </td>
                                        <td>
                                            <c:forEach var="p" items="${t.salaryEmployeeDetails}" varStatus="loop">
                                                <c:if test="${p.key eq '{calculated_salary}'}">
                                                    <c:out value="${p.value}" />
                                                </c:if>
                                            </c:forEach>
                                        </td>
                                        <td nowrap class="text-center">
                                            <%--<c:set var="view" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'view')}"/>
                                            <c:choose>
                                                <c:when test="${view.status}">
                                                    <a href="javascript:viewData('<c:out value="${utl:toJson(t)}" />')" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${view.object.name}"/>">
                                                        <i class="la <c:out value="${view.object.icon.name}"/>"></i>
                                                    </a>
                                                </c:when>
                                            </c:choose>
                                            <c:set var="detail" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'detail')}"/>
                                            <c:choose>
                                                <c:when test="${detail.status}">
                                                    <a href="#" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${detail.object.name}"/>">
                                                        <i class="la <c:out value="${detail.object.icon.name}"/>"></i>
                                                    </a>
                                                </c:when>
                                            </c:choose>--%>
                                        </td>
                                    </tr>
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
                </div>
                </form:form>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="modal-view" tabindex="-1" role="dialog">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Maaş detallarla</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Bağla</button>
            </div>
        </div>
    </div>
</div>

<script src="<c:url value="/assets/js/demo4/pages/crud/datatables/advanced/row-grouping.js" />" type="text/javascript"></script>

<script>
    <c:if test="${view.status}">
        $('#kt_table_1 tbody').on('dblclick', 'tr', function () {
            var ref = $(this).find('td:first').text();
            alert(ref);
        });
    </c:if>
    <c:if test="${detail.status}">
    $('#kt_table_2 tbody').on('dblclick', 'tr', function () {
        var ref = $(this).find('td:first').text();
        alert(ref);
    });
    </c:if>
    function viewData(data){
        data = data.replace(/\&#034;/g, '"');
        var obj = jQuery.parseJSON(data);
        console.log(obj);
        $('#modal-view').modal('toggle');
    }
</script>


