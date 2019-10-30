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
                <form:form modelAttribute="form" id="form" method="post" action="/payroll/working-hour-record" cssClass="form-group">
                    <input name="id" type="hidden" value="<c:out value="${form.id}"/>" />
                <c:set var="search" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'search')}"/>
                <c:choose>
                    <c:when test="${search.status}">
                        <div class="kt-portlet__head kt-portlet__head--lg">
                            <div class="kt-portlet__head-title" style="width: 100%">
                                <div class="row">
                                    <div class="col-sm-2">
                                        <div class="form-group">
                                            <form:label path="branch">&nbsp;</form:label>
                                            <form:select  path="branch" cssClass="custom-select form-control">
                                                <c:forEach var="t" items="${branches}" varStatus="loop">
                                                    <c:set var="selected" value="${t.id==form.branch.id?'selected':''}"/>
                                                    <option value="<c:out value="${t.id}"/>" <c:out value="${selected}"/>><c:out value="${t.name}"/></option>
                                                </c:forEach>
                                            </form:select>
                                            <form:errors path="branch" cssClass="control-label alert alert-danger" />
                                        </div>
                                    </div>
                                    <div class="col-sm-2">
                                        <div class="form-group">
                                            <label>&nbsp;</label>
                                            <input name="monthYear" class="form-control" type="month" value="<c:out value="${form.monthYear}"/>" />
                                        </div>
                                    </div>
                                    <div class="col-sm-3">
                                        <label>&nbsp;</label>
                                        <div class="form-group">
                                            <a href="#" onclick="submit($('#form'))" class="btn btn-brand btn-elevate btn-icon-sm" title="<c:out value="${search.object.name}"/>">
                                                <i class="la <c:out value="${search.object.icon}"/>"></i>
                                                <c:out value="${search.object.name}"/>
                                            </a>
                                            <button type="reset" class="btn btn-secondary btn-secondary--icon">
                                                <i class="la la-close"></i>
                                                Təmizlə
                                            </button>
                                        </div>
                                    </div>
                                    <div class="col-sm-5 text-right">
                                        <label>&nbsp;</label>
                                        <div class="form-group">
                                            <c:set var="save" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'save')}"/>
                                            <c:choose>
                                                <c:when test="${save.status}">
                                                    <c:if test="${not empty form.workingHourRecordEmployees and !form.approve}">
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
                                                    <c:if test="${not empty form.workingHourRecordEmployees and !form.approve}">
                                                        <a href="#" onclick="approveData($('#form'), '<c:out value="${form.month}"/>.<c:out value="${form.year}"/> tarixli iş vaxtının uçotu')" class="btn btn-success btn-elevate btn-icon-sm" title="<c:out value="${approve.object.name}"/>">
                                                            <i class="la <c:out value="${approve.object.icon}"/>"></i>
                                                            <c:out value="${approve.object.name}"/>
                                                        </a>
                                                    </c:if>
                                                </c:when>
                                            </c:choose>
                                            <c:set var="cancel" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'cancel')}"/>
                                            <c:choose>
                                                <c:when test="${cancel.status}">
                                                    <c:if test="${not empty form.workingHourRecordEmployees and form.approve}">
                                                        <a href="#" onclick="approveData($('#form'), '<c:out value="${form.month}"/>.<c:out value="${form.year}"/> tarixli iş vaxtının uçotu')" class="btn btn-dark btn-elevate btn-icon-sm" title="<c:out value="${cancel.object.name}"/>">
                                                            <i class="la <c:out value="${cancel.object.icon}"/>"></i>
                                                            <c:out value="${cancel.object.name}"/>
                                                        </a>
                                                    </c:if>
                                                </c:when>
                                            </c:choose>
                                            <c:set var="reload" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'reload')}"/>
                                            <c:choose>
                                                <c:when test="${reload.status}">
                                                    <c:if test="${not empty form.workingHourRecordEmployees and !form.approve}">
                                                        <a href="javascript:reloadWHR($('#form'))" class="btn btn-info btn-elevate btn-icon-sm" title="<c:out value="${reload.object.name}"/>">
                                                            <i class="la <c:out value="${reload.object.icon}"/>"></i>
                                                            <c:out value="${reload.object.name}"/>
                                                        </a>
                                                    </c:if>
                                                </c:when>
                                            </c:choose>
                                            <c:set var="delete" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'delete')}"/>
                                            <c:choose>
                                                <c:when test="${delete.status}">
                                                    <c:if test="${not empty form.workingHourRecordEmployees}">
                                                        <a href="javascript:deleteData('<c:out value="${form.id}" />', '<c:out value="${form.month}"/>.<c:out value="${form.year}"/> tarixli iş vaxtının uçotu');" class="btn btn-danger btn-elevate btn-icon-sm" title="<c:out value="${delete.object.name}"/>">
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
                    <c:set var="edit" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'edit')}"/>
                    <c:choose>
                        <c:when test="${not empty form.workingHourRecordEmployees}">
                            <table class="table table-striped- table-bordered table-hover table-checkable" id="kt_table_3">
                                <thead>
                                <tr>
                                    <th rowspan="3">№</th>
                                    <th rowspan="3" ><div style="width: 220px !important;">Ad Soyad Ata adı</div></th>
                                    <th colspan="2" rowspan="2" class="text-center" style="letter-spacing: 4px;">Əməkdaş</th>
                                    <th colspan="<c:out value="${days_in_month}"/>" class="text-center" style="letter-spacing: 4px;">
                                        Ayın və həftənin günləri
                                    </th>
                                    <th colspan="<c:out value="${identifiers.size()}"/>" rowspan="2" class="text-center" style="letter-spacing: 4px;">Günlər</th>
                                </tr>
                                <tr>
                                    <c:forEach var = "i" begin = "1" end = "${days_in_month}">
                                        <th class="bg-info text-center kt-padding-1" style="border: none; color: white;"><c:out value = "${i}"/></th>
                                    </c:forEach>
                                </tr>
                                <tr>
                                    <th class="bg-warning" style="border: none;"><div style="width: 180px !important;">Vəzifə</div></th>
                                    <th class="bg-warning" style="border: none;"><div style="width: 120px !important;">Struktur</div></th>
                                    <c:forEach var = "i" begin = "1" end = "${days_in_month}">
                                        <th class="bg-info text-center kt-padding-0" style="border: none; color: white;"><c:out value = "${utl:weekDay(i, form.month, form.year)}"/></th>
                                    </c:forEach>
                                    <c:forEach var="p" items="${identifiers}" varStatus="count">
                                        <th class="bg-danger text-center" style="color:white; border: none;"><c:out value="${p.attr1}"/></th>
                                    </c:forEach>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="t" items="${form.workingHourRecordEmployees}" varStatus="loop">
                                    <tr>
                                        <td class="text-center">
                                            ${loop.index + 1}
                                            <input type="hidden" name="workingHourRecordEmployees[${loop.index}].id" value="<c:out value="${t.id}"/>"/>
                                            <input type="hidden" name="workingHourRecordEmployees[${loop.index}].workingHourRecord" value="<c:out value="${form.id}"/>"/>
                                            <input type="hidden" name="workingHourRecordEmployees[${loop.index}].employee.id" value="<c:out value="${t.employee.id}"/>"/>
                                            <input type="hidden" name="workingHourRecordEmployees[${loop.index}].fullName" value="<c:out value="${t.fullName}"/>"/>
                                            <input type="hidden" name="workingHourRecordEmployees[${loop.index}].position" value="<c:out value="${t.position}"/>"/>
                                            <input type="hidden" name="workingHourRecordEmployees[${loop.index}].organization" value="<c:out value="${t.organization}"/>"/>
                                        </td>
                                        <th>
                                            <c:out value="${t.fullName}"/>
                                        </th>
                                        <td>
                                            <c:out value="${t.position}"/>
                                        </td>
                                        <td>
                                            <c:out value="${t.organization}"/>
                                        </td>
                                        <c:forEach var="p" items="${t.workingHourRecordEmployeeIdentifiers}" varStatus="count">
                                            <td class="text-center kt-padding-0">
                                                <c:choose>
                                                    <c:when test="${!form.approve}">
                                                        <div class="typeahead">
                                                            <input type="hidden" name="workingHourRecordEmployees[${loop.index}].workingHourRecordEmployeeIdentifiers[${count.index}].id" value="<c:out value="${p.id}"/>"/>
                                                            <input type="hidden" name="workingHourRecordEmployees[${loop.index}].workingHourRecordEmployeeIdentifiers[${count.index}].workingHourRecordEmployee" value="<c:out value="${t.id}"/>"/>
                                                            <input type="hidden" name="workingHourRecordEmployees[${loop.index}].workingHourRecordEmployeeIdentifiers[${count.index}].weekDay" value="<c:out value="${p.weekDay}"/>"/>
                                                            <input type="hidden" name="workingHourRecordEmployees[${loop.index}].workingHourRecordEmployeeIdentifiers[${count.index}].monthDay" value="<c:out value="${p.monthDay}"/>"/>
                                                            <input type="text" class="type-ahead" name="workingHourRecordEmployees[${loop.index}].workingHourRecordEmployeeIdentifiers[${count.index}].identifier" value="<c:out value="${p.identifier}"/>"/>
                                                        </div>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <label style="padding-top: 11px; font-weight: bold; width: 50px; height: 34px;"><c:out value="${p.identifier}"/></label>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                        </c:forEach>
                                        <c:forEach var="p" items="${t.workingHourRecordEmployeeDayCalculations}" varStatus="count">
                                            <th class="text-center">
                                                <c:choose>
                                                    <c:when test="${p.key!='HMQ'}">
                                                        <fmt:formatNumber value="${p.value}" maxFractionDigits="0" />
                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:out value="${p.value}"/>
                                                    </c:otherwise>
                                                </c:choose>
                                            </th>
                                        </c:forEach>
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

    <div class="alert alert-info alert-elevate" role="alert">
        <div class="alert-icon"><i class="flaticon-warning kt-font-brand kt-font-light"></i></div>
        <div class="alert-text">
            <c:forEach var="t" items="${identifiers}" varStatus="loop">
                [&nbsp;<span style="font-weight: 400; font-size: 13px"><c:out value="${t.attr1}"/></span> - <c:out value="${t.name}"/>&nbsp;]&nbsp;&nbsp;&nbsp;
            </c:forEach>
        </div>
        <div class="alert-close">
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true"><i class="la la-close"></i></span>
            </button>
        </div>
    </div>
</div>

<script src="<c:url value="/assets/vendors/general/typeahead.js/dist/typeahead.bundle.js" />" type="text/javascript"></script>
<script src="<c:url value="/assets/vendors/general/typeahead.js/dist/typeahead.jquery.js" />" type="text/javascript"></script>

<script>
    var KTTypeahead = function() {

        var states = [
            <c:forEach var="t" items="${identifiers}" varStatus="loop">
                '<c:out value="${t.attr1}"/>',
            </c:forEach>
        ];

        var demo1 = function() {
            var substringMatcher = function(strs) {
                return function findMatches(q, cb) {
                    var matches, substringRegex;
                    matches = [];
                    substrRegex = new RegExp(q, 'i');
                    $.each(strs, function(i, str) {
                        if (substrRegex.test(str)) {
                            matches.push(str);
                        }
                    });
                    cb(matches);
                };
            };

            $('.type-ahead').typeahead({
                hint: true,
                highlight: true,
                minLength: 0,
                items: 'all'
            }, {
                name: 'states',
                source: substringMatcher(states)
            });
        };
        return {
            init: function() {
                demo1();
            }
        };
    }();

    var KTDatatablesBasicScrollable = function() {
        var initTable2 = function() {
            var table = $('#kt_table_3');
            table.DataTable({
                scrollY: 400,
                scrollX: true,
                paging: false,
                autoWidth: false,
                searching: false,
                columnDefs: [
                    {orderable: false, targets: 0}
                ],
                fixedColumns:   {
                    leftColumns: 2
                },
                order: [[1, 'asc']]
            });
        };
        return {
            init: function() {
                initTable2();
            }
        };
    }();

    KTTypeahead.init();
    KTDatatablesBasicScrollable.init();

    function saveWHR(form){
        $(form).attr("action", "/payroll/working-hour-record/save");
        submit(form)
    }

    function reloadWHR(form){
        swal.fire({
            title: 'Əminsinizmi?',
            html: 'Məzuniyyət, Ezamiyyət, Xəstəlik və İşə davamiyyət məlumatları yenilənəcəkdir. Yenilənmədən öncə dəyişib yadda saxlamadığınız məlumat varsa, YADDA SAXLA əməliyyatını etməlisiniz',
            type: 'info',
            allowEnterKey: true,
            showCancelButton: true,
            buttonsStyling: false,
            cancelButtonText: 'İmtina',
            cancelButtonColor: '#d1d5cf',
            cancelButtonClass: 'btn btn-default',
            confirmButtonText: 'Bəli, icra edilsin!',
            confirmButtonColor: '#c40000',
            confirmButtonClass: 'btn btn-info',
            footer: '<a href>Məlumatlar yenilənsinmi?</a>'
        }).then(function(result) {
            if (result.value) {
                swal.fire({
                    text: 'Proses davam edir...',
                    onOpen: function() {
                        $(form).attr("action", "/payroll/working-hour-record/reload");
                        swal.showLoading();
                        $(form).submit();
                    }
                })
            }
        })
    }

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
                    onOpen: function() {
                        $(form).attr("action", "/payroll/working-hour-record/approve");
                        swal.showLoading();
                        $(form).submit();
                    }
                })
            }
        })
    }

</script>


