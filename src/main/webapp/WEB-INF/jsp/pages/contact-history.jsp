<%@ page import="java.util.Date" %><%--
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
                        <form:form modelAttribute="filter" id="filter" method="post" action="/collect/contact-history/filter">
                            <form:hidden path="organization" />
                            <div class="row">
                                <div class="col-md-11">
                                    <div class="row">
                                        <div class="col-md-4">
                                            <div class="row">
                                                <div class="col-md-4">
                                                    <div class="form-group">
                                                        <form:label path="id">KOD</form:label>
                                                        <form:input path="id" cssClass="form-control" placeholder="######"/>
                                                        <form:errors path="id" cssClass="control-label alert-danger"/>
                                                    </div>
                                                </div>
                                                <div class="col-md-4">
                                                    <div class="form-group">
                                                        <form:label path="sales">Satış kodu</form:label>
                                                        <form:input path="sales" cssClass="form-control" placeholder="#######" />
                                                        <form:errors path="sales" cssClass="alert-danger"/>
                                                    </div>
                                                </div>
                                                <div class="col-md-4">
                                                    <div class="form-group">
                                                        <form:label path="childSales">Servis kodu</form:label>
                                                        <form:input path="childSales" cssClass="form-control" placeholder="#######" />
                                                        <form:errors path="childSales" cssClass="alert-danger"/>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="contactChannel">Əlaqə kanalı</form:label>
                                                <form:select  path="contactChannel.id" cssClass="custom-select form-control select2-single" multiple="single">
                                                    <form:option value=""></form:option>
                                                    <form:options items="${contact_channels}" itemLabel="name" itemValue="id"/>
                                                </form:select>
                                                <form:errors path="contactChannel" cssClass="control-label alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="description">Açıqlama</form:label>
                                                <form:input path="description" cssClass="form-control" placeholder="Açıqlama daxil edin"/>
                                                <form:errors path="description" cssClass="alert alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="nextContactDateFrom">Tarixdən</form:label>
                                                <div class="input-group date">
                                                    <form:input path="nextContactDateFrom" autocomplete="off"
                                                                cssClass="form-control datepicker-element" date_="date_"
                                                                placeholder="dd.MM.yyyy"/>
                                                    <div class="input-group-append">
                                        <span class="input-group-text">
                                            <i class="la la-calendar"></i>
                                        </span>
                                                    </div>
                                                </div>
                                                <form:errors path="nextContactDateFrom" cssClass="control-label alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="nextContactDate">Tarixədək</form:label>
                                                <div class="input-group date">
                                                    <form:input path="nextContactDate" autocomplete="off"
                                                                cssClass="form-control datepicker-element" date_="date_"
                                                                placeholder="dd.MM.yyyy"/>
                                                    <div class="input-group-append">
                                        <span class="input-group-text">
                                            <i class="la la-calendar"></i>
                                        </span>
                                                    </div>
                                                </div>
                                                <form:errors path="nextContactDate" cssClass="control-label alert-danger"/>
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
                            <c:set var="edit" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'edit')}"/>
                            <c:set var="view" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'view')}"/>
                            <c:set var="view3" value="${utl:checkOperation(sessionScope.user.userModuleOperations, 'sales', 'view')}"/>
                            <table class="table table-striped- table-bordered table-hover table-checkable" id="datatable">
                                <thead>
                                <tr>
                                    <th>№</th>
                                    <th>ID</th>
                                    <th>Struktur</th>
                                    <th>Satış</th>
                                    <th>Əlaqə kanalı</th>
                                    <th>Əlaqə tarixi</th>
                                    <th>Növbəti əlaqə tarixi</th>
                                    <th>Servis</th>
                                    <th>Açıqlama</th>
                                    <th>Əməliyyat</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="t" items="${list.content}" varStatus="loop">
                                    <tr data="<c:out value="${t.id}"/>" sales="<c:out value="${t.sales.id}"/>">
                                        <td>${loop.index + 1}</td>
                                        <td><c:out value="${t.id}"/></td>
                                        <td><c:out value="${t.organization.name}"/></td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${view3.status}">
                                                    <c:choose>
                                                        <c:when test="${t.sales.service}">
                                                            <a href="javascript:window.open('/sale/service/<c:out value="${t.sales.id}" />', 'mywindow', 'width=1250, height=800')" class="kt-link kt-font-bolder"><c:out value="${t.sales.id}" /></a>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <a href="javascript:window.open('/sale/sales/<c:out value="${t.sales.id}" />', 'mywindow', 'width=1250, height=800')" class="kt-link kt-font-bolder"><c:out value="${t.sales.id}" /></a>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:when>
                                                <c:otherwise>
                                                    <c:out value="${t.sales.id}" />
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td><c:out value="${t.contactChannel.name}"/></td>
                                        <td><fmt:formatDate value = "${t.createdDate}" pattern = "dd.MM.yyyy" /></td>
                                        <td><fmt:formatDate value = "${t.nextContactDate}" pattern = "dd.MM.yyyy" /></td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${view3.status}">
                                                    <c:choose>
                                                        <c:when test="${t.childSales.service}">
                                                            <a href="javascript:window.open('/sale/service/<c:out value="${t.childSales.id}" />', 'mywindow', 'width=1250, height=800')" class="kt-link kt-font-bolder"><c:out value="${t.childSales.id}" /></a>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <a href="javascript:window.open('/sale/sales/<c:out value="${t.childSales.id}" />', 'mywindow', 'width=1250, height=800')" class="kt-link kt-font-bolder"><c:out value="${t.childSales.id}" /></a>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:when>
                                                <c:otherwise>
                                                    <c:out value="${t.childSales.id}" />
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td style="max-width: 500px"><c:out value="${t.description}"/></td>
                                        <td nowrap class="text-center">
                                            <c:if test="${view.status}">
                                                <a href="javascript:contactHistory('view', $('#form'), '<c:out value="${t.id}" />', 'modal-operation', '<c:out value="${view.object.name}" />', '<c:out value="${t.sales.id}"/>');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${view.object.name}"/>">
                                                    <i class="<c:out value="${view.object.icon}"/>"></i>
                                                </a>
                                            </c:if>
                                            <c:if test="${edit.status}">
                                                <a href="javascript:contactHistory('edit', $('#form'), '<c:out value="${t.id}" />', 'modal-operation', '<c:out value="${edit.object.name}" />', '<c:out value="${t.sales.id}"/>');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${edit.object.name}"/>">
                                                    <i class="<c:out value="${edit.object.icon}"/>"></i>
                                                </a>
                                            </c:if>
                                            <c:if test="${delete.status}">
                                                <a href="javascript:deleteData('<c:out value="${t.id}" />', '<c:out value="${t.description}"/>');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${delete.object.name}"/>">
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
                <h5 class="modal-title">Başlıq</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form:form modelAttribute="form" id="form" method="post" action="/collect/contact-history" cssClass="form-group">
                    <form:hidden path="id"/>
                    <form:hidden path="organization"/>
                    <form:hidden path="childSales"/>
                    <div class="form-group">
                        <form:label path="sales">Satış kodu</form:label>
                        <div class="input-group date" >
                            <div class="input-group-prepend"><span class="input-group-text"><i class="la la-calculator"></i></span></div>
                            <form:input path="sales" cssClass="form-control" placeholder="Daxil edin"/>
                        </div>
                        <form:errors path="sales" cssClass="control-label alert-danger" />
                    </div>
                    <div class="form-group">
                        <form:label path="contactChannel">Əlaqə yaradılmışdır</form:label><br/>
                        <c:forEach var="t" items="${contact_channels}" varStatus="loop">
                            <label class="kt-radio kt-radio--brand">
                                <form:radiobutton path="contactChannel" value="${t.id}"/> <c:out value="${t.name}"/>
                                <span></span>
                            </label>
                            &nbsp;&nbsp;&nbsp;&nbsp;
                        </c:forEach>
                        <form:errors path="contactChannel" cssClass="control-label alert-danger" />
                    </div>
                    <div class="form-group">
                        <form:label path="nextContactDate">Növbəti əlaqə taixi</form:label>
                        <div class="input-group date" >
                            <div class="input-group-prepend"><span class="input-group-text"><i class="la la-calendar"></i></span></div>
                            <form:input path="nextContactDate" autocomplete="off" date_="date_" cssClass="form-control datepicker-element" placeholder="dd.MM.yyyy"/>
                        </div>
                        <form:errors path="nextContactDate" cssClass="control-label alert-danger" />
                    </div>
                    <div class="form-group">
                        <form:label path="description">Açıqlama</form:label>
                        <form:textarea path="description" cssClass="form-control" rows="5"/>
                        <form:errors path="description" cssClass="control-label alert-danger" />
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
    <c:if test="${view.status}">
    $('#datatable tbody').on('dblclick', 'tr', function () {
        contactHistory('view', $('#form'), $(this).attr('data'), 'modal-operation', '<c:out value="${view.object.name}" />', $(this).attr('sales'));
    });
    </c:if>

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
            sales: {
                required: true
            },
            description: {
                required: true
            }
        },
        invalidHandler: function(event, validator) {
            KTUtil.scrollTop();
            swal.close();
        },
    });

    $("input[name='id']").inputmask('decimal', {
        rightAlignNumerics: false
    });

    $("input[name='sales']").inputmask('decimal', {
        rightAlignNumerics: false
    });

    $("input[name='childSales']").inputmask('decimal', {
        rightAlignNumerics: false
    });

    function contactHistory(oper, form, dataId, modal, modal_title, salesId){
        swal.fire({
            text: 'Proses davam edir...',
            allowOutsideClick: false,
            onOpen: function() {
                swal.showLoading();
                $.ajax({
                    url: '/collect/api/contact-history/'+dataId,
                    type: 'GET',
                    dataType: 'text',
                    beforeSend: function() {
                        $(form).find("input[name='sales']").val('');
                    },
                    success: function(data) {
                        if(oper==="view"){
                            view(form, data, modal, modal_title)
                        } else if(oper==="edit"){
                            edit(form, data, modal, modal_title)
                        }
                        $(form).find("input[name='sales']").val(salesId);
                        swal.close();
                    },
                    error: function() {
                        swal.fire({
                            title: "Xəta",
                            html: "Xəta baş verdi!",
                            type: "error",
                            cancelButtonText: 'Bağla',
                            cancelButtonColor: '#c40000',
                            cancelButtonClass: 'btn btn-danger',
                            footer: '<a href>Məlumatlar yenilənsinmi?</a>'
                        });
                    }
                })
            }
        });
    }
</script>





