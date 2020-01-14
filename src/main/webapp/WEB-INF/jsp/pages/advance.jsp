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
    <c:set var="delete" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'delete')}"/>
    <c:set var="filter" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'filter')}"/>
    <c:if test="${filter.status}">
        <div class="accordion  accordion-toggle-arrow mb-2" id="accordionFilter">
            <div class="card" style="border-radius: 4px;">
                <div class="card-header">
                    <div class="card-title w-100" data-toggle="collapse" data-target="#filterContent" aria-expanded="true" aria-controls="collapseOne4">
                        <div class="row w-100">
                            <div class="col-3">
                                <i class="<c:out value="${filter.object.icon}"/>"></i>
                                <c:out value="${list.totalElements>0?list.totalElements:0} sətr"/>
                            </div>
                            <div class="col-6 text-center" style="letter-spacing: 10px;">
                                <c:out value="${filter.object.name}"/>
                            </div>
                        </div>
                    </div>
                </div>
                <div id="filterContent" class="collapse show" aria-labelledby="headingOne" data-parent="#accordionFilter">
                    <div class="card-body">
                        <form:form modelAttribute="filter" id="filter" method="post" action="/payroll/advance/filter">
                            <form:hidden path="organization" />
                            <div class="row">
                                <div class="col-md-11">
                                    <div class="row">
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="id">KOD</form:label>
                                                <form:input path="id" cssClass="form-control" placeholder="######"/>
                                                <form:errors path="id" cssClass="control-label alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="advance">Avans</form:label>
                                                <form:select path="advance.id" cssClass="custom-select form-control">
                                                    <form:option value=""></form:option>
                                                    <form:options items="${advances}" itemLabel="name" itemValue="id"/>
                                                </form:select>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="employee">Əməkdaş</form:label>
                                                <form:input path="employee" cssClass="form-control" placeholder="Əməkdaşın kodu" />
                                                <form:errors path="employee" cssClass="alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="payedFrom">Qiymətdən</form:label>
                                                <form:input path="payedFrom" cssClass="form-control" placeholder="Məbləği daxil edin"/>
                                                <form:errors path="payedFrom" cssClass="alert-danger control-label"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="payed">Qiymətədək</form:label>
                                                <form:input path="payed" cssClass="form-control" placeholder="Məbləği daxil edin"/>
                                                <form:errors path="payed" cssClass="alert-danger control-label"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="advanceDateFrom">Tarixdən</form:label>
                                                <div class="input-group date">
                                                    <form:input path="advanceDateFrom" autocomplete="off"
                                                                cssClass="form-control datepicker-element" date="date"
                                                                placeholder="dd.MM.yyyy"/>
                                                    <div class="input-group-append">
                                        <span class="input-group-text">
                                            <i class="la la-calendar"></i>
                                        </span>
                                                    </div>
                                                </div>
                                                <form:errors path="advanceDateFrom" cssClass="control-label alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="advanceDate">Tarixədək</form:label>
                                                <div class="input-group date">
                                                    <form:input path="advanceDate" autocomplete="off"
                                                                cssClass="form-control datepicker-element" date="date"
                                                                placeholder="dd.MM.yyyy"/>
                                                    <div class="input-group-append">
                                        <span class="input-group-text">
                                            <i class="la la-calendar"></i>
                                        </span>
                                                    </div>
                                                </div>
                                                <form:errors path="advanceDate" cssClass="control-label alert-danger"/>
                                            </div>
                                        </div>
                                        <c:if test="${delete.status}">
                                            <div class="col-md-2" style="padding-top: 30px;">
                                                <div class="form-group">
                                                    <label class="kt-checkbox kt-checkbox--brand">
                                                        <form:checkbox path="active"/> Aktual məlumat
                                                        <span></span>
                                                    </label>
                                                </div>
                                            </div>
                                        </c:if>
                                    </div>
                                </div>
                                <div class="col-md-1 text-right">
                                    <div class="form-group">
                                        <a href="#" onclick="location.reload();" class="btn btn-danger btn-elevate btn-icon-sm btn-block mb-2" style="padding: 0.35rem 0.6rem;">
                                            <i class="la la-trash"></i> Sil
                                        </a>
                                        <a href="#" onclick="submit($('#filter'))" class="btn btn-warning btn-elevate btn-icon-sm btn-block mt-2" style="padding: 0.35rem 0.6rem">
                                            <i class="la la-search"></i> Axtar
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>
    </c:if>
    <div class="row">
        <div class="col-lg-12">
            <div class="kt-portlet kt-portlet--mobile">
                <div class="kt-portlet__body">

<c:choose>
    <c:when test="${not empty list}">
        <c:set var="approve" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'approve')}"/>
        <c:set var="edit" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'edit')}"/>
        <table class="table table-striped- table-bordered table-hover table-checkable" id="kt_table_1">
    <thead>
    <tr>
        <th>№</th>
        <th>ID</th>
        <th>Əməkdaş</th>
        <th>Struktur</th>
        <th>Məbləğ</th>
        <th>Tarix</th>
        <th>Açıqlama</th>
        <th>Formul</th>
        <th>Təsdiq edilib</th>
        <th>Status</th>
        <th>Əməliyyat</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="t" items="${list.content}" varStatus="loop">
        <tr data="<c:out value="${utl:toJson(t)}" />">
            <td>${loop.index + 1}</td>
            <td><c:out value="${t.id}" /></td>
            <th><div style="width: 160px;"><c:out value="${t.employee.person.fullName}" /></div></th>
            <td><c:out value="${t.organization.name}" /></td>
            <th>
                <div style="width: 65px;">
                    <span><c:out value="${t.payed}" /></span>
                    <span class="kt-font-bold font-italic font-size-10px">AZN</span>
                </div>
            </th>
            <th><fmt:formatDate value = "${t.advanceDate}" pattern = "dd.MM.yyyy" /></th>
            <td><c:out value="${t.description}" /></td>
            <td><c:out value="${t.formula}" /></td>
            <td>
                <c:if test="${t.approve}">
                    <fmt:formatDate value = "${t.approveDate}" pattern = "dd.MM.yyyy HH:mm:ss" />
                </c:if>
            </td>
            <th class="text-center">
                <c:choose>
                    <c:when test="${t.debt}">
                        <i class="la la-arrow-down kt-font-bold kt-font-success"></i>
                    </c:when>
                    <c:when test="${!t.debt}">
                        <i class="la la-arrow-up kt-font-bold kt-font-danger" style="font-weight: bold;"></i>
                    </c:when>
                </c:choose>
            </th>
            <td nowrap class="text-center">
                <c:if test="${approve.status && !t.approve}">
                    <a href="javascript:approve($('#advance-approve-form'), $('#advance-approve-modal'), '<c:out value="${t.id}" />', '<c:out value="${t.description}" />', '<c:out value="${t.payed}" />');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${approve.object.name}"/>">
                        <i class="<c:out value="${approve.object.icon}"/>"></i>
                    </a>
                </c:if>
                <c:if test="${edit.status && !t.approve}">
                    <a href="javascript:edit($('#form'), '<c:out value="${utl:toJson(t)}" />', 'modal-operation', '<c:out value="${edit.object.name}" />');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${edit.object.name}"/>">
                        <i class="<c:out value="${edit.object.icon}"/>"></i>
                    </a>
                </c:if>
                <c:if test="${delete.status}">
                    <a href="javascript:deleteData('<c:out value="${t.id}" />', '<c:out value="${t.description}" />');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${delete.object.name}"/>">
                        <i class="<c:out value="${delete.object.icon}"/>"></i>
                    </a>
                </c:if>
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
                <form:form modelAttribute="form" id="form" method="post" action="/payroll/advance" cssClass="form-group">
                    <form:hidden path="id"/>
                    <form:hidden path="organization" />
                    <form:hidden path="approve"/>
                    <form:hidden path="approveDate"/>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <form:label path="employee">Əməkdaş</form:label>
                                <form:select  path="employee" cssClass="custom-select form-control">
                                    <form:options items="${employees}" itemLabel="person.fullName" itemValue="id" />
                                </form:select>
                                <form:errors path="employee" cssClass="control-label alert alert-danger" />
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <form:label path="advance">Avans</form:label>
                                <form:select  path="advance" cssClass="custom-select form-control">
                                    <form:options items="${advances}" itemLabel="name" itemValue="id" />
                                </form:select>
                                <form:errors path="advance" cssClass="control-label alert alert-danger" />
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-4">
                            <div class="form-group">
                                <form:label path="payed">Məbləğ</form:label>
                                <div class="input-group" >
                                    <form:input path="payed" cssClass="form-control" placeholder="Məbləği daxil edin"/>
                                    <div class="input-group-append">
                                                    <span class="input-group-text">
                                                        <i class="la la-usd"></i>
                                                    </span>
                                    </div>
                                </div>
                                <form:errors path="payed" cssClass="alert-danger control-label"/>
                            </div>
                        </div>
                        <div class="col-md-5">
                            <div class="form-group">
                                <form:label path="advanceDate">Avans tarixi</form:label>
                                <div class="input-group date" >
                                    <form:input path="advanceDate" cssClass="form-control datepicker-element" date="date" placeholder="dd.MM.yyyy"/>
                                    <div class="input-group-append">
                                        <span class="input-group-text">
                                            <i class="la la-calendar"></i>
                                        </span>
                                    </div>
                                </div>
                                <form:errors path="advanceDate" cssClass="control-label alert-danger" />
                            </div>
                        </div>
                        <div class="col-md-3" style="padding-top: 30px;">
                            <div class="form-group">
                                <label class="kt-checkbox kt-checkbox--brand">
                                    <form:checkbox path="debt"/> Ödənişdirmi?
                                    <span></span>
                                </label>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <form:label path="description">Açıqlama</form:label>
                        <form:textarea path="description" cssClass="form-control"/>
                        <form:errors path="description" cssClass="alert-danger control-label"/>
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

<div class="modal fade" id="advance-approve-modal" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title"></h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form:form modelAttribute="form" id="advance-approve-form" method="post" action="/payroll/advance/approve" cssClass="form-group">
                    <form:hidden path="id"/>
                    <div class="form-group">
                        <form:label path="description">Açıqlama</form:label>
                        <form:textarea path="description" cssClass="form-control"/>
                        <form:errors path="description" cssClass="alert-danger control-label"/>
                    </div>
                    <div class="row">
                        <div class="col-sm-12">
                            <div class="form-group">
                                <form:label path="payed">Məbləğ</form:label>
                                <div class="input-group" >
                                    <form:input path="payed" cssClass="form-control" placeholder="Məbləği daxil edin"/>
                                    <div class="input-group-append">
                                                    <span class="input-group-text">
                                                        <i class="la la-usd"></i>
                                                    </span>
                                    </div>
                                </div>
                                <form:errors path="payed" cssClass="alert-danger control-label"/>
                            </div>
                        </div>
                    </div>
                </form:form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="submit($('#advance-approve-form'));">Bəli, təsdiq edirəm!</button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Bağla</button>
            </div>
        </div>
    </div>
</div>

<script>
    function approve(form, modal, id, description, payed){
        $(form).find("#id").val(id);
        $(form).find("#payed").val(payed);
        $(form).find("#description").val(description);

        $(modal).find(".modal-title").html('Təsdiq et!');
        $(modal).modal('toggle');
    }
</script>

<script>
    <c:if test="${edit.status}">
    $('#kt_table_1 tbody').on('dblclick', 'tr', function () {
        edit($('#form'), $(this).attr('data'), 'modal-operation', 'Redaktə');
    });
    </c:if>
</script>


