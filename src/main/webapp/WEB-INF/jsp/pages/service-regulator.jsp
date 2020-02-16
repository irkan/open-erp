<%--
  Created by IntelliJ IDEA.
  User: irkan.ahmadov
  Date: 20.09.2019
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
                        <form:form modelAttribute="filter" id="filter" method="post" action="/sale/sales-regulator/filter">
                            <form:hidden path="sales.organization" />
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
                                                <form:label path="sales">Satış</form:label>
                                                <form:input path="sales" cssClass="form-control" placeholder="Satış kodu" />
                                                <form:errors path="sales" cssClass="alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="servicedDateFrom">Tarixdən</form:label>
                                                <div class="input-group date">
                                                    <form:input path="servicedDateFrom" autocomplete="off"
                                                                cssClass="form-control datepicker-element" date="date"
                                                                placeholder="dd.MM.yyyy"/>
                                                    <div class="input-group-append">
                                        <span class="input-group-text">
                                            <i class="la la-calendar"></i>
                                        </span>
                                                    </div>
                                                </div>
                                                <form:errors path="servicedDateFrom" cssClass="control-label alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="servicedDate">Tarixədək</form:label>
                                                <div class="input-group date">
                                                    <form:input path="servicedDate" autocomplete="off"
                                                                cssClass="form-control datepicker-element" date="date"
                                                                placeholder="dd.MM.yyyy"/>
                                                    <div class="input-group-append">
                                        <span class="input-group-text">
                                            <i class="la la-calendar"></i>
                                        </span>
                                                    </div>
                                                </div>
                                                <form:errors path="servicedDate" cssClass="control-label alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="serviceNotification">Filter</form:label>
                                                <form:select path="serviceNotification.id" cssClass="custom-select form-control">
                                                    <form:option value=""></form:option>
                                                    <form:options items="${service_notifications}" itemLabel="name" itemValue="id"/>
                                                </form:select>
                                            </div>
                                        </div>
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
                            <c:set var="edit" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'edit')}"/>
                            <table class="table table-striped- table-bordered table-hover table-checkable" id="datatable">
                                <thead>
                                <tr>
                                    <th>ID #</th>
                                    <th>Satış nömrəsi</th>
                                    <th>Xəbərdarlıq tarixi</th>
                                    <th>Filter</th>
                                    <th>Əməliyyat</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="t" items="${list.content}" varStatus="loop">
                                    <tr data="<c:out value="${utl:toJson(t)}" />">
                                        <td><span class="kt-padding-5"><c:out value="${t.id}" /></span></td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${t.sales.service}">
                                                    <a href="javascript:window.open('/sale/service/<c:out value="${t.sales.id}" />', 'mywindow', 'width=1250, height=800')" class="kt-link kt-font-bolder"><c:out value="${t.sales.id}" /></a>
                                                </c:when>
                                                <c:otherwise>
                                                    <a href="javascript:window.open('/sale/sales/<c:out value="${t.sales.id}" />', 'mywindow', 'width=1250, height=800')" class="kt-link kt-font-bolder"><c:out value="${t.sales.id}" /></a>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <fmt:formatDate value = "${t.servicedDate}" pattern = "dd.MM.yyyy" />
                                        </td>
                                        <td>
                                            <c:out value="${t.serviceNotification.name}"/>
                                        </td>
                                        <td nowrap class="text-center">
                                            <c:if test="${edit.status}">
                                                <a href="javascript:edit($('#form'), '<c:out value="${utl:toJson(t)}" />', 'modal-operation', '<c:out value="${edit.object.name}" />');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${edit.object.name}"/>">
                                                    <i class="la <c:out value="${edit.object.icon}"/>"></i>
                                                </a>
                                            </c:if>
                                            <c:if test="${delete.status}">
                                                <a href="javascript:deleteData('<c:out value="${t.id}" />', '<c:out value="${t.serviceNotification.name}" />');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${delete.object.name}"/>">
                                                    <i class="la <c:out value="${delete.object.icon}"/>"></i>
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

<div class="modal fade" id="modal-operation" tabindex="100" role="dialog">
    <div class="modal-dialog modal-sm" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title"></h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form:form modelAttribute="form" id="form" method="post" action="/sale/service-regulator" cssClass="form-group">
                    <form:hidden path="id"/>
                    <div class="form-group">
                        <form:label path="sales">Satış KOD</form:label>
                        <form:input path="sales" cssClass="form-control" placeholder="Satış kodu" />
                        <form:errors path="sales" cssClass="alert-danger"/>
                    </div>
                    <div class="form-group">
                        <form:label path="serviceNotification">Filter</form:label>
                        <form:select path="serviceNotification" cssClass="custom-select form-control">
                            <form:option value=""></form:option>
                            <form:options items="${service_notifications}" itemLabel="name" itemValue="id"/>
                        </form:select>
                    </div>
                    <div class="form-group">
                        <form:label path="servicedDate">Xəbərdarlıq tarixi</form:label>
                        <div class="input-group date">
                            <div class="input-group-prepend"><span class="input-group-text"><i class="la la-calendar"></i></span></div>
                            <form:input path="servicedDate" autocomplete="off" cssClass="form-control datepicker-element" date="date" placeholder="dd.MM.yyyy"/>
                        </div>
                        <form:errors path="servicedDate" cssClass="control-label alert-danger"/>
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

<script>
    $("#datatable").DataTable({
        responsive: true,
        lengthMenu: [10, 25, 50, 75, 100, 200, 1000],
        pageLength: 100,
        order: [[1, 'desc']],
        columnDefs: [
            {
                targets: 0,
                width: '25px',
                className: 'dt-center',
                orderable: false
            },
        ],
    });

    $( "#form" ).validate({
        rules: {
            serviceNotification: {
                required: true
            }
        },
        invalidHandler: function(event, validator) {
            KTUtil.scrollTop();
            swal.close();
        },
    })

</script>