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
                    <c:set var="detail" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'detail')}"/>
                    <c:set var="export" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'export')}"/>
                    <c:set var="approve" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'approve')}"/>
    <c:choose>
    <c:when test="${not empty list}">
        <table class="table table-striped- table-bordered table-hover table-checkable" id="group_table">
    <thead>
    <tr>
        <th>ID</th>
        <th style="min-width: 160px;">Əməkdaş</th>
        <th>Struktur</th>
        <th>Təsdiq edilməmiş ümumi say</th>
        <th>Təsdiq edilməmiş ümumi məbləğ</th>
        <th>Təsdiq edilməmiş cari aylıq say</th>
        <th>Təsdiq edilməmiş cari aylıq məbləğ</th>
        <th>Təsdiq edilmiş ümumi say</th>
        <th>Təsdiq edilmiş ümumi məbləğ</th>
        <th>Təsdiq edilmiş cari aylıq say</th>
        <th>Təsdiq edilmiş cari aylıq məbləğ</th>
        <%--<th>Hesablanmış məbləğ</th>
        <th>Götürülmüş məbləğ aylıq</th>
        <th>Götürülmüş məbləğ</th>
        <th>Təsdiq gözləyir</th>--%>
        <th>Əməliyyat</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="t" items="${list}" varStatus="loop">
        <tr data="<c:out value="${t.employee.id}" />">
            <td><c:out value="${t.employee.id}" /></td>
            <td data-sort="<c:out value="${t.employee.person.fullName}" />"><span style="width: 160px;" class="kt-font-bolder"><c:out value="${t.employee.person.fullName}" /></span></td>
            <td><c:out value="${t.employee.organization.name}" /></td>
            <td data-sort="<c:out value="${t.report.integer1}" />"><c:out value="${t.report.integer1 gt 0 ? t.report.integer1+=' ədəd' : '' }" /></td>
            <td data-sort="<c:out value="${t.report.double1}" />"><c:out value="${t.report.double1 gt 0 ? t.report.double1+=' AZN' : '' }" /></td>
            <td data-sort="<c:out value="${t.report.integer2}" />"><c:out value="${t.report.integer2 gt 0 ? t.report.integer2+=' ədəd' : '' }" /></td>
            <td data-sort="<c:out value="${t.report.double2}" />"><c:out value="${t.report.double2 gt 0 ? t.report.double2+=' AZN' : '' }" /></td>
            <td data-sort="<c:out value="${t.report.integer3}" />"><c:out value="${t.report.integer3 gt 0 ? t.report.integer3+=' ədəd' : '' }" /></td>
            <td data-sort="<c:out value="${t.report.double3}" />"><c:out value="${t.report.double3 gt 0 ? t.report.double3+=' AZN' : '' }" /></td>
            <td data-sort="<c:out value="${t.report.integer4}" />"><c:out value="${t.report.integer4 gt 0 ? t.report.integer4+=' ədəd' : '' }" /></td>
            <td data-sort="<c:out value="${t.report.double4}" />"><c:out value="${t.report.double4 gt 0 ? t.report.double4+=' AZN' : '' }" /></td>
            <td nowrap class="text-center">
                <c:if test="${approve.status and t.report.integer1 gt 0}">
                    <a href="javascript:approveData($('#approve-form'),'<c:out value="${t.report.integer1}" /> sayda əməliyyatın təsdiqi', '<c:out value="${t.employee.id}" />');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${approve.object.name}"/>">
                        <i class="<c:out value="${approve.object.icon}"/>"></i>
                    </a>
                </c:if>
                <c:if test="${detail.status}">
                    <a href="/payroll/advance/<c:out value="${t.employee.id}"/>" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${detail.object.name}"/>">
                        <i class="la <c:out value="${detail.object.icon}"/>"></i>
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

<form:form modelAttribute="form" id="approve-form" method="post" action="/payroll/advance-group/approve" style="display: none">
    <input type="hidden" name="employee.id" />
</form:form>

<script>

    $('#group_table tbody').on('dblclick', 'tr', function () {
        <c:if test="${detail.status}">
        swal.showLoading();
        location.href = '/payroll/advance/'+ $(this).attr('data');
        window.reload();
        </c:if>
    });

    $('#group_table').DataTable({
        <c:if test="${export.status}">
        dom: 'B<"clear">lfrtip',
        buttons: [
            $.extend( true, {}, buttonCommon, {
                extend: 'copyHtml5'
            } ),
            $.extend( true, {}, buttonCommon, {
                extend: 'csvHtml5'
            } ),
            $.extend( true, {}, buttonCommon, {
                extend: 'excelHtml5'
            } ),
            $.extend( true, {}, buttonCommon, {
                extend: 'pdfHtml5'
            } ),
            $.extend( true, {}, buttonCommon, {
                extend: 'print'
            } )
        ],
        </c:if>
        responsive: true,
        fixedHeader: {
            headerOffset: $('#kt_header').outerHeight()
        },
        pageLength: 100,
        order: [[2, 'asc']],
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

    function approveData(form, info, id){
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
                        $(form).find("input[name='employee.id']").val(id);
                        swal.showLoading();
                        $(form).submit();
                    }
                })
            }
        })
    }
</script>