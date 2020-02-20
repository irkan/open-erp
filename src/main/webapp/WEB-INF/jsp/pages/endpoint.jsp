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
                        <form:form modelAttribute="filter" id="filter" method="post" action="/admin/endpoint/filter">
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
                                                <form:label path="connectionType">Tip</form:label>
                                                <form:select path="connectionType.id" cssClass="custom-select form-control">
                                                    <form:option value=""></form:option>
                                                    <form:options items="${connection_types}" itemLabel="name" itemValue="id"/>
                                                </form:select>
                                                <form:errors path="connectionType" cssClass="alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="description">Açıqlama</form:label>
                                                <form:input path="description" cssClass="form-control" placeholder="Açıqlama daxil edin"/>
                                                <form:errors path="description" cssClass="alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="lastStatusDateFrom">Tarixdən</form:label>
                                                <div class="input-group date">
                                                    <form:input path="lastStatusDateFrom" autocomplete="off"
                                                                cssClass="form-control datepicker-element" date_="date_"
                                                                placeholder="dd.MM.yyyy"/>
                                                    <div class="input-group-append">
                                        <span class="input-group-text">
                                            <i class="la la-calendar"></i>
                                        </span>
                                                    </div>
                                                </div>
                                                <form:errors path="lastStatusDateFrom" cssClass="control-label alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="lastStatusDate">Tarixədək</form:label>
                                                <div class="input-group date">
                                                    <form:input path="lastStatusDate" autocomplete="off"
                                                                cssClass="form-control datepicker-element" date_="date_"
                                                                placeholder="dd.MM.yyyy"/>
                                                    <div class="input-group-append">
                                        <span class="input-group-text">
                                            <i class="la la-calendar"></i>
                                        </span>
                                                    </div>
                                                </div>
                                                <form:errors path="lastStatusDate" cssClass="control-label alert-danger"/>
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
                            <c:set var="view" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'view')}"/>
                            <table class="table table-striped- table-bordered table-hover table-checkable" id="datatable">
                                <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Bağlanma tipi</th>
                                    <th>Struktur</th>
                                    <th>Tarix</th>
                                    <th>Say</th>
                                    <th>Açıqlama</th>
                                    <th>Yaradılma tarixi</th>
                                    <th>Əməliyyat</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="t" items="${list.content}" varStatus="loop">
                                     <tr data="<c:out value="${utl:toJson(t)}" />">
                                         <td><c:out value="${t.id}" /></td>
                                         <th><c:out value="${t.connectionType.name}" /></th>
                                         <th><fmt:formatDate value = "${t.demonstrateDate}" pattern = "dd.MM.yyyy" /></th>
                                         <td><c:out value="${t.amount}" /></td>
                                         <td><c:out value="${t.description}" /></td>
                                         <td><fmt:formatDate value = "${t.createdDate}" pattern = "dd.MM.yyyy HH:mm" /></td>
                                         <td nowrap class="text-center">
                                             <c:if test="${view.status}">
                                                 <a href="javascript:view($('#form'), '<c:out value="${utl:toJson(t)}" />', 'modal-operation', '<c:out value="${view.object.name}" />');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${view.object.name}"/>">
                                                     <i class="<c:out value="${view.object.icon}"/>"></i>
                                                 </a>
                                             </c:if>
                                             <c:if test="${delete.status}">
                                                 <a href="javascript:deleteData('<c:out value="${t.id}" />', '<fmt:formatDate value = "${t.demonstrateDate}" pattern = "dd.MM.yyyy" />');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${delete.object.name}"/>">
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
    <div class="modal-dialog modal-sm" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Yeni sorğu yarat</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form:form modelAttribute="form" id="form" method="post" action="/sale/demonstration" cssClass="form-group">
                    <form:hidden path="id"/>
                    <form:hidden path="active"/>
                    <form:hidden path="organization"/>
                    <div class="form-group">
                        <form:label path="employee">Əməkdaş</form:label>
                        <form:select  path="employee" cssClass="custom-select form-control select2-single">
                            <form:option value=""></form:option>
                            <c:forEach var="itemGroup" items="${employees}" varStatus="itemGroupIndex">
                                <optgroup label="${itemGroup.key}">
                                    <form:options items="${itemGroup.value}" itemLabel="person.fullName" itemValue="id"/>
                                </optgroup>
                            </c:forEach>
                        </form:select>
                        <form:errors path="employee" cssClass="control-label alert-danger"/>
                    </div>
                    <div class="row">
                        <div class="col-7">
                            <div class="form-group">
                                <form:label path="demonstrateDate">Nümayiş tarixi</form:label>
                                <div class="input-group date">
                                    <div class="input-group-prepend"><span class="input-group-text"><i class="la la-calendar"></i></span></div>
                                    <form:input path="demonstrateDate" autocomplete="off" date_="date_" cssClass="form-control datepicker-element" placeholder="dd.MM.yyyy"/>
                                </div>
                                <form:errors path="demonstrateDate" cssClass="control-label alert-danger" />
                            </div>
                        </div>
                        <div class="col-5">
                            <div class="form-group">
                                <form:label path="amount">Say</form:label>
                                <div class="input-group date">
                                    <div class="input-group-prepend"><span class="input-group-text"><i class="la la-calculator"></i></span></div>
                                    <form:input path="amount" autocomplete="off" cssClass="form-control" placeholder="Sayı daxil edin"/>
                                </div>
                                <form:errors path="amount" cssClass="control-label alert-danger" />
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

    $('#datatable tbody').on('dblclick', 'tr', function () {
        <c:if test="${view.status}">
        view($('#form'), $(this).attr('data'), 'modal-operation', '<c:out value="${view.object.name}" />');
        </c:if>
    });

    $( "#form" ).validate({
        rules: {
            connectionType: {
                required: true
            },
            port: {
                required: false,
                digits: true,
                min: 1
            }
        },
        invalidHandler: function(event, validator) {
            KTUtil.scrollTop();
            swal.close();
        },
    });

</script>


