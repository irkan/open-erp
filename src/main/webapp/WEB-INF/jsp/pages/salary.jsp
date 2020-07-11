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
                    <input name="workingHourRecord.organization" type="hidden" value="<c:out value="${sessionScope.organization.id}"/>" />
                <c:set var="calculate" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'calculate')}"/>
                <c:choose>
                    <c:when test="${calculate.status}">
                        <div class="kt-portlet__head kt-portlet__head--lg">
                            <div class="kt-portlet__head-title" style="width: 100%">
                                <div class="row">
                                    <div class="col-sm-3">
                                        <div class="form-group">
                                            <label>&nbsp;</label>
                                            <form:input path="workingHourRecord.monthYear" cssClass="form-control" type="month"/>
                                        </div>
                                    </div>
                                    <div class="col-sm-3">
                                        <label>&nbsp;</label>
                                        <div class="form-group">
                                            <a href="#" onclick="submit($('#form'))" class="btn btn-brand btn-elevate btn-icon-sm" title="<c:out value="${calculate.object.name}"/>">
                                                <i class="la <c:out value="${calculate.object.icon}"/>"></i>
                                                <c:out value="${calculate.object.name}"/>
                                            </a>
                                        </div>
                                    </div>
                                    <div class="col-sm-6 text-right">
                                        <label>&nbsp;</label>
                                        <div class="form-group">
                                            <c:set var="approve" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'approve')}"/>
                                            <c:choose>
                                                <c:when test="${approve.status}">
                                                    <c:if test="${not empty form.salaryEmployees and !form.approve}">
                                                        <a href="#" onclick="approveData($('#form'), '<c:out value="${form.workingHourRecord.month}"/>.<c:out value="${form.workingHourRecord.year}"/> tarixli iş vaxtının uçotu')" class="btn btn-success btn-elevate btn-icon-sm" title="<c:out value="${approve.object.name}"/>">
                                                            <i class="la <c:out value="${approve.object.icon}"/>"></i>
                                                            <c:out value="${approve.object.name}"/>
                                                        </a>
                                                    </c:if>
                                                </c:when>
                                            </c:choose>
                                            <c:set var="cancel" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'cancel')}"/>
                                            <c:choose>
                                                <c:when test="${cancel.status}">
                                                    <c:if test="${not empty form.salaryEmployees and form.approve}">
                                                        <a href="#" onclick="approveData($('#form'), '<c:out value="${form.month}"/>.<c:out value="${form.year}"/> tarixli iş vaxtının uçotu')" class="btn btn-dark btn-elevate btn-icon-sm" title="<c:out value="${cancel.object.name}"/>">
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
                                                        <a href="javascript:deleteData('<c:out value="${form.id}" />', '<c:out value="${form.workingHourRecord.month}"/>.<c:out value="${form.workingHourRecord.year}"/> tarixli maaş hesablanması ');" class="btn btn-danger btn-elevate btn-icon-sm" title="<c:out value="${delete.object.name}"/>">
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
                            <c:set var="view" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'view')}"/>
                            <c:set var="export" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'export')}"/>
                            <c:set var="detail" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'detail')}"/>
                            <table class="table table-striped- table-bordered table-hover table-checkable" id="kt_table_1">
                                <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Ad Soyad Ata adı</th>
                                    <th>Ümumi əmək haqqı (Rəsmi hissə)</th>
                                    <th>Hesablanmış əmək haqqı (Rəsmi hissə)</th>
                                    <th>Ümumi əmək haqqı</th>
                                    <th>Hesablanmış əmək haqqı (Qeyri rəsmi hissə)</th>
                                    <th>Hesablanmış avans</th>
                                    <th>Yekun ödəniləcək məbləğ (Rəsmi hissə)</th>
                                    <th>Yekun ödəniləcək məbləğ (Qeyri rəsmi hissə)</th>
                                    <th>Əməliyyat</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="t" items="${form.salaryEmployees}" varStatus="loop">
                                    <tr valign="middle" class="text-justify" data="<c:out value="${utl:toJson(t)}" />">
                                        <td><c:out value="${t.id}" /></td>
                                        <th><div style="width: 190px"><c:out value="${t.workingHourRecordEmployee.fullName}" /></div></th>
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
                                        <th>
                                            <c:forEach var="p" items="${t.salaryEmployeeDetails}" varStatus="loop">
                                                <c:if test="${p.key eq '{calculated_advance}'}">
                                                    <c:out value="${p.value}" />
                                                </c:if>
                                            </c:forEach>
                                        </th>
                                        <th>
                                            <c:forEach var="p" items="${t.salaryEmployeeDetails}" varStatus="loop">
                                                <c:if test="${p.key eq '{total_amount_payable_official}'}">
                                                    <c:out value="${p.value}" />
                                                </c:if>
                                            </c:forEach>
                                        </th>
                                        <th>
                                            <c:forEach var="p" items="${t.salaryEmployeeDetails}" varStatus="loop">
                                                <c:if test="${p.key eq '{total_amount_payable_non_official}'}">
                                                    <c:out value="${p.value}" />
                                                </c:if>
                                            </c:forEach>
                                        </th>
                                        <td nowrap class="text-center">
                                            <c:if test="${view.status}">
                                                <a href="javascript:viewData('<c:out value="${utl:toJson(t)}" />')" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${view.object.name}"/>">
                                                    <i class="la <c:out value="${view.object.icon}"/>"></i>
                                                </a>
                                            </c:if>
                                            <c:if test="${detail.status}">
                                                <a href="/payroll/salary-employee/<c:out value="${t.employee.id}"/>" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${detail.object.name}"/>">
                                                    <i class="la <c:out value="${detail.object.icon}"/>"></i>
                                                </a>
                                            </c:if>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                            <div class="row">
                                <div class="col-10 text-right">
                                    Ümumi DSMF ayırmaları:
                                </div>
                                <div class="col-2">
                                    <fmt:formatNumber value="${form.sumOfDsmfDeduction}" type="number" maxFractionDigits="2"/><c:out value=" ₼"/>
                                    <div class="kt-separator kt-separator--border-2x kt-separator--space-sm kt-separator--portlet-fit mb-3"></div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-10 text-right">
                                    Ümumi gəlir vergisi:
                                </div>
                                <div class="col-2">
                                    <fmt:formatNumber value="${form.sumOfTaxIncome}" type="number" maxFractionDigits="2"/><c:out value=" ₼"/>
                                    <div class="kt-separator kt-separator--border-2x kt-separator--space-sm kt-separator--portlet-fit mb-3"></div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-10 text-right">
                                    Ümumi həmkarlar təşkilatına üzvlük haqqı:
                                </div>
                                <div class="col-2">
                                    <fmt:formatNumber value="${form.sumOfMembershipFeeForTradeUnion}" type="number" maxFractionDigits="2"/><c:out value=" ₼"/>
                                    <div class="kt-separator kt-separator--border-2x kt-separator--space-sm kt-separator--portlet-fit mb-3"></div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-10 text-right">
                                    Ümumi icbari tibbi sığorta haqqı:
                                </div>
                                <div class="col-2">
                                    <fmt:formatNumber value="${form.sumOfCompulsoryHealthInsurance}" type="number" maxFractionDigits="2"/><c:out value=" ₼"/>
                                    <div class="kt-separator kt-separator--border-2x kt-separator--space-sm kt-separator--portlet-fit mb-3"></div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-10 text-right">
                                    Ümumi işsizlikdən sığorta haqqı:
                                </div>
                                <div class="col-2">
                                    <fmt:formatNumber value="${form.sumOfUnemploymentInsurance}" type="number" maxFractionDigits="2"/><c:out value=" ₼"/>
                                    <div class="kt-separator kt-separator--border-2x kt-separator--space-sm kt-separator--portlet-fit mb-3"></div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-10 text-right">
                                    Ümumi yekun ödəniləcək məbləğ (Rəsmi hissə):
                                </div>
                                <div class="col-2">
                                    <fmt:formatNumber value="${form.sumOfTotalAmountPayableOfficial}" type="number" maxFractionDigits="2"/><c:out value=" ₼"/>
                                    <div class="kt-separator kt-separator--border-2x kt-separator--space-sm kt-separator--portlet-fit mb-3"></div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-10 text-right">
                                    Ümumi yekun ödəniləcək məbləğ (Qeyri rəsmi hissə):
                                </div>
                                <div class="col-2">
                                    <fmt:formatNumber value="${form.sumOfTotalAmountPayableNonOfficial}" type="number" maxFractionDigits="2"/><c:out value=" ₼"/>
                                    <div class="kt-separator kt-separator--border-2x kt-separator--space-sm kt-separator--portlet-fit mb-3"></div>
                                </div>
                            </div>
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
    <div class="modal-dialog modal-xl" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Maaş detallarla</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <table class="table table-striped- table-bordered table-hover table-checkable" id="group_table">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Skeleton Formula</th>
                        <th>Ad Soyad Ata adı</th>
                        <th>Açıqlama</th>
                        <th>Key</th>
                        <th>Dəyər</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Bağla</button>
            </div>
        </div>
    </div>
</div>

<form id="approve-form" method="post" action="/payroll/salary/approve">
    <input type="hidden" name="salary">
</form>

<script>
    <c:if test="${view.status}">
        $('#kt_table_1 tbody').on('dblclick', 'tr', function () {
            viewData($(this).attr('data'))
        });
    </c:if>
    function viewData(data){
        data = data.replace(/\&#034;/g, '"');
        var obj = jQuery.parseJSON(data);
        console.log(obj);
        KTDatatablesAdvancedRowGrouping.init(obj.salaryEmployeeDetails, obj.workingHourRecordEmployee.fullName);
        $('#modal-view').modal('toggle');
    }

    var KTDatatablesAdvancedRowGrouping = function() {

        var initTable1 = function(data, fullName) {
            var table = $('#group_table');

            table.DataTable({
                responsive: true,
                fixedHeader: {
                   headerOffset: $('#kt_header').outerHeight()
                },
                pageLength: 100,
                bDestroy: true,
                data: data,
                order: [[2, 'asc']],
                columns: [
                    { "data": 'id' },
                    { "data": 'formula' },
                    { "defaultContent": fullName },
                    { "data": 'description' },
                    { "data": 'key' },
                    { "data": 'value' }
                ],
                drawCallback: function(settings) {
                    var api = this.api();
                    var rows = api.rows({page: 'current'}).nodes();
                    var last = null;

                    api.column(2, {page: 'current'}).data().each(function(group, i) {
                        if (last !== group) {
                            $(rows).eq(i).before(
                                '<tr class="group"><td colspan="30">' + group + '</td></tr>'
                            );
                            last = group;
                        }
                    });
                },
                columnDefs: [
                    {
                        targets: [2],
                        visible: false
                    }
                ]
            });
        };

        return {
            init: function(data, fullName) {
                initTable1(data, fullName);
            }
        };
    }();

    function approveData(form, info){
        swal.fire({
            title: 'Əminsinizmi?',
            html: info,
            type: 'success',
            allowEnterKey: true,
            showCancelButton: true,
            buttonsStyling: false,
            cancelButtonText: 'İmtina',
            cancelButtonColor: '#d1d5cf',
            cancelButtonClass: 'btn btn-default',
            confirmButtonText: 'Bəli, icra edilsin!',
            confirmButtonColor: '#c40000',
            confirmButtonClass: 'btn btn-success',
            footer: '<a href>Məlumatlar yenilənsinmi?</a>'
        }).then(function(result) {
            if (result.value) {
                swal.fire({
                    text: 'Proses davam edir...',
                    allowOutsideClick: false,
                    onOpen: function() {
                        $(form).attr("action", "/payroll/salary/approve");
                        swal.showLoading();
                        $(form).submit();
                    }
                })
            }
        })
    }
</script>


