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
    <div class="row">
        <div class="col-lg-12">
            <div class="kt-portlet kt-portlet--mobile">
               <div class="kt-portlet__body">
                    <c:choose>
                        <c:when test="${not empty list}">
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
                                <c:forEach var="t" items="${list}" varStatus="loop">
                                    <c:forEach var="p" items="${t.salaryEmployeeDetails}" varStatus="loop">
                                        <tr>
                                            <td><c:out value="${p.id}" /></td>
                                            <td><c:out value="${p.skeletonFormula}" /> = <c:out value="${p.formula}" /></td>
                                            <td>
                                                <fmt:setLocale value="en_US" />
                                                <fmt:formatDate value="${utl:generate(1, t.salary.workingHourRecord.month, t.salary.workingHourRecord.year)}" pattern="MMMM" type="both" />,
                                                <c:out value="${t.salary.workingHourRecord.year}" /> -
                                                <c:out value="${t.workingHourRecordEmployee.fullName}" />
                                            </td>
                                            <td><c:out value="${p.description}" /></td>
                                            <td><c:out value="${p.key}" /></td>
                                            <td><c:out value="${p.value}" /></td>
                                        </tr>
                                    </c:forEach>
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
    var KTDatatablesAdvancedRowGrouping = function() {

        var initTable1 = function() {
            var table = $('#group_table');

            table.DataTable({
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
                                '<tr class="group"><td colspan="30" class="p-3 kt-bg-info kt-font-light">' + group + '</td></tr>'
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
            init: function() {
                initTable1();
            }
        };
    }();

    jQuery(document).ready(function() {
        KTDatatablesAdvancedRowGrouping.init();
    });
</script>



