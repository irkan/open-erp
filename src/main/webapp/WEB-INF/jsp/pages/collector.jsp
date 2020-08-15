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
<style>

    td { position: relative; }

    tr.strikeout td:before {
        content: " ";
        position: absolute;
        top: 45%;
        left: 0;
        border-bottom: 1px solid #e50f00;
        width: 100%;
    }

    tr.strikeout td:after {
        content: "\00B7";
        font-size: 1px;
    }

</style>
<div class="kt-container  kt-grid__item kt-grid__item--fluid">
    <div class="row">
        <div class="col-lg-12">
            <div class="kt-portlet kt-portlet--mobile">
                <div class="kt-portlet__body">
                    <c:choose>
                        <c:when test="${not empty list}">
                            <c:set var="detail" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'detail')}"/>
                            <c:set var="export" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'export')}"/>
                            <table class="table table-striped- table-bordered table-hover table-checkable" id="group_table">
                                <thead>
                                <tr>
                                    <th>HF nömrəsi</th>
                                    <th>Satış|Servis</th>
                                    <th>Status</th>
                                    <th>Müştəri</th>
                                    <th>Məbləğ</th>
                                    <th>Tarix</th>
                                    <th>Yığımçı</th>
                                    <th>Kanal</th>
                                    <th>Referans</th>
                                    <th>Avans</th>
                                    <th>Əməliyyat</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="t" items="${list.content}" varStatus="loop">
                                    <tr data="<c:out value="${utl:toJson(t)}" />" class="<c:out value="${(t.price lt 0 and t.approve)?'strikeout':''}"/>">
                                        <td><span class="kt-padding-5"><c:out value="${t.id}" /></span></td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${t.sales.service}">
                                                    <a href="javascript:window.open('/sale/service/<c:out value="${t.sales.id}" />', 'mywindow', 'width=1250, height=800')" class="kt-link kt-font-bolder">Servis: <c:out value="${t.sales.id}" /></a>
                                                </c:when>
                                                <c:otherwise>
                                                    <a href="javascript:window.open('/sale/sales/<c:out value="${t.sales.id}" />', 'mywindow', 'width=1250, height=800')" class="kt-link kt-font-bolder">Satış: <c:out value="${t.sales.id}" /></a>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${!t.approve}">
                                                    Təsdiq edilməyənlər
                                                </c:when>
                                                <c:otherwise>
                                                    Təsdiqlənənlər
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <a href="javascript:window.open('/crm/customer/<c:out value="${t.sales.customer.id}"/>', 'mywindow', 'width=1250, height=800')" class="kt-link kt-font-bolder"><c:out value="${t.sales.customer.id}" />: <c:out value="${t.sales.customer.person.fullName}"/></a>
                                        </td>
                                        <td><c:out value="${t.price}" /> AZN</td>
                                        <td><fmt:formatDate value = "${t.invoiceDate}" pattern = "dd.MM.yyyy" /></td>
                                        <td><c:out value="${t.collector.person.fullName}" /></td>
                                        <td><c:out value="${t.paymentChannel.name}" /></td>
                                        <td><c:out value="${t.channelReferenceCode}" /></td>
                                        <td class="text-center">
                                            <c:if test="${t.advance}">
                                                <i class="flaticon2-check-mark kt-font-success"></i>
                                            </c:if>
                                        </td>
                                        <td nowrap class="text-center">
                                            <c:if test="${detail.status}">
                                                <a href="/warehouse/action/<c:out value="${t.id}"/>" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${detail.object.name}"/>">
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

<script>
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
</script>




