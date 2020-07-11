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
    <div class="row">
        <div class="col-lg-12">
            <div class="kt-portlet kt-portlet--mobile">
                <div class="kt-portlet__body">
                    <c:choose>
                        <c:when test="${not empty list}">
                            <c:set var="detail" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'detail')}"/>
                            <c:set var="export" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'export')}"/>
                            <c:set var="admin" value="${utl:isAdministrator(sessionScope.user)}"/>
                            <table class="table table-striped- table-bordered table-hover table-checkable" id="group_table">
                                <thead>
                                <tr>
                                    <th>Satış №</th>
                                    <th>Müştəri</th>
                                    <th>Struktur</th>
                                    <th>Müştəri ilə əlaqə</th>
                                    <c:if test="${admin}">
                                        <th>Servis əməkdaşı</th>
                                    </c:if>
                                    <th>Tarix</th>
                                    <th>Sorğu</th>
                                    <th>Qeyd</th>
                                    <th>Əməliyyat</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="t" items="${list.content}" varStatus="loop">
                                    <tr>
                                        <td style="min-width: 80px">
                                            <c:if test="${not empty t.id}">
                                                <a href="javascript:copyToClipboard2('<c:out value="${t.id}" />', 'Satış kodu <b><c:out value="${t.id}" /></b> kopyalandı')" class="kt-font-lg kt-font-bold kt-font-info kt-font-hover-danger pl-2 pr-2"><i class="la la-copy"></i></a>
                                            </c:if>
                                            <c:choose>
                                                <c:when test="${t.service}">
                                                    <a href="javascript:window.open('/sale/service/<c:out value="${t.id}" />', 'mywindow', 'width=1250, height=800')" class="kt-link kt-font-bolder"><c:out value="${t.id}" /></a>
                                                </c:when>
                                                <c:otherwise>
                                                    <a href="javascript:window.open('/sale/sales/<c:out value="${t.id}" />', 'mywindow', 'width=1250, height=800')" class="kt-link kt-font-bolder"><c:out value="${t.id}" /></a>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td style="min-width: 210px">
                                            <c:if test="${not empty t.customer.id}">
                                                <a href="javascript:copyToClipboard2('<c:out value="${t.customer.id}" />', 'Müştəri kodu <b><c:out value="${t.customer.id}" /></b> kopyalandı')" class="kt-font-lg kt-font-bold kt-font-info kt-font-hover-danger pl-2 pr-2"><i class="la la-copy"></i></a>
                                            </c:if>
                                            <a href="javascript:window.open('/crm/customer/<c:out value="${t.customer.id}"/>', 'mywindow', 'width=1250, height=800')" class="kt-link kt-font-bolder"><c:out value="${t.customer.person.fullName}"/></a>
                                        </td>
                                        <td style="min-width: 110px">
                                            <c:out value="${t.organization.name}" />
                                        </td>
                                        <td>
                                            <div>
                                                <a href="#" class="kt-link kt-font-bolder kt-font-danger"><c:out value="${t.customer.person.contact.mobilePhone}"/></a>
                                                <a href="#" class="kt-link kt-font-bolder kt-font-warning"><c:out value="${t.customer.person.contact.homePhone}"/></a>
                                                <a href="#" class="kt-link kt-font-bolder"><c:out value="${t.customer.person.contact.relationalPhoneNumber1}"/></a>
                                                <a href="#" class="kt-link kt-font-bolder"><c:out value="${t.customer.person.contact.relationalPhoneNumber2}"/></a>
                                                <a href="#" class="kt-link kt-font-bolder"><c:out value="${t.customer.person.contact.relationalPhoneNumber3}"/></a>
                                            </div>
                                            <div>
                                                <c:out value="${t.customer.person.contact.city.name}"/>
                                                <c:out value="${t.customer.person.contact.address}"/>
                                            </div>
                                        </td>
                                        <c:if test="${admin}">
                                            <td><c:out value="${t.servicer.person.fullName}" /></td>
                                        </c:if>
                                        <td><fmt:formatDate value = "${t.saleDate}" pattern = "dd.MM.yyyy hh:mm" /></td>
                                        <td><c:out value="${t.payment.description}" /></td>
                                        <td style="min-width: 200px;"><c:out value="${t.serviceNotice}" /></td>
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
            'copy', 'csv', 'excel', 'pdf', 'print'
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




