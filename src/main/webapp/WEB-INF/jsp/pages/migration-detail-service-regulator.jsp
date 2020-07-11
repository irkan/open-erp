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
                    <c:choose>
                        <c:when test="${not empty list}">
                            <c:set var="view" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'view')}"/>
<c:set var="export" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'export')}"/>
                            <c:set var="detail" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'detail')}"/>
                            <c:set var="reload" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'reload')}"/>
                            <c:set var="delete" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'delete')}"/>
                            <table class="table table-striped- table-bordered table-hover table-checkable" id="datatable">
                                <thead>
                                <tr>
                                    <th>№</th>
                                    <th>Status</th>
                                    <th>Əməliyyat</th>
                                    <th>Servis tarixi</th>
                                    <th>Müştəri</th>
                                    <th>Son qiymət</th>
                                    <th>İlkin ödəniş</th>
                                    <th>Ödənilib</th>
                                    <th>Ödəniş periodu</th>
                                    <th>Ödəniş periodu kodu</th>
                                    <th>Ödəniş qrafiki</th>
                                    <th>Ödəniş qrafiki kodu</th>
                                    <th>Ven lider</th>
                                    <th>Ven lider kodu</th>
                                    <th>Nağdırmı?</th>
                                    <th>Müştəri</th>
                                    <th>Satış</th>
                                    <th>Satıcı kodu</th>
                                    <th>Servis işçisi</th>
                                    <th>Servis işçisi kodu</th>
                                    <th>Xətalar</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="t" items="${list}" varStatus="loop">
                                    <tr data="<c:out value="${t.id}" />">
                                        <td><c:out value="${t.id}" /></td>
                                        <td><c:out value="${t.status}" /></td>
                                        <td nowrap class="text-center">
                                            <c:if test="${delete.status and  t.status eq 1}">
                                                <a href="javascript:deleteData('<c:out value="${t.id}" />', '<c:out value="${t.customerFullName}"/>');" class="dbtn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${delete.object.name}"/>">
                                                    <i class="la la-play"></i>
                                                </a>
                                            </c:if>
                                            <c:if test="${view.status}">
                                                <a href="javascript:;" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${view.object.name}"/>">
                                                    <i class="<c:out value="${view.object.icon}"/>"></i>
                                                </a>
                                            </c:if>
                                            <c:if test="${detail.status}">
                                                <a href="/admin/migration-detail/<c:out value="${t.id}"/>" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${detail.object.name}"/>">
                                                    <i class="<c:out value="${detail.object.icon}"/>"></i>
                                                </a>
                                            </c:if>
                                            <c:if test="${reload.status}">
                                                <a href="javascript:migrationReload('<c:out value="${t.id}" />');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${reload.object.name}"/>">
                                                    <i class="<c:out value="${reload.object.icon}"/>"></i>
                                                </a>
                                            </c:if>
                                            <c:if test="${delete.status}">
                                                <a href="javascript:deleteData('<c:out value="${t.id}" />', '<c:out value="${t.customerFullName}"/>');" class="dbtn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${delete.object.name}"/>">
                                                    <i class="<c:out value="${delete.object.icon}"/>"></i>
                                                </a>
                                            </c:if>
                                        </td>
                                        <td><c:out value="${t.salesInventoryName}" /></td>
                                        <td><fmt:formatDate value = "${t.salesDate}" pattern = "dd.MM.yyyy" /></td>
                                        <td><c:out value="${t.customerFullName}" /></td>
                                        <td><c:out value="${t.salesPaymentLastPrice}" /></td>
                                        <td><c:out value="${t.salesPaymentDown}" /></td>
                                        <td><c:out value="${t.salesPaymentPayed}" /></td>
                                        <td><c:out value="${t.salesPaymentPeriod}" /></td>
                                        <td><c:out value="${t.period.id}" /> <c:out value="${t.period.name}" /></td>
                                        <td><c:out value="${t.salesPaymentSchedule}" /></td>
                                        <td><c:out value="${t.schedule.id}" /> <c:out value="${t.schedule.name}" /></td>
                                        <td><c:out value="${t.employeeVanLeader}" /></td>
                                        <td><c:out value="${t.vanLeader.id}" /> <c:out value="${t.vanLeader.person.fullName}" /></td>
                                        <td><c:out value="${t.salesPaymentCash}" /></td>
                                        <td><c:out value="${t.salesPaymentGift}" /></td>
                                        <td><c:out value="${t.salesSaled}" /></td>
                                        <th><c:out value="${t.customerContactAddress}"/></th>
                                        <td><c:out value="${t.customerContactPhoneNumbers}" /></td>
                                        <td><c:out value="${t.employeeConsole}" /></td>
                                        <td><c:out value="${t.console.id}" /> <c:out value="${t.console.person.fullName}" /></td>
                                        <td><c:out value="${t.employeeCanvasser}" /></td>
                                        <td><c:out value="${t.canvasser.id}" /> <c:out value="${t.canvasser.person.fullName}" /></td>
                                        <td><c:out value="${t.employeeDealer}" /></td>
                                        <td><c:out value="${t.dealer.id}" /> <c:out value="${t.dealer.person.fullName}" /></td>
                                        <td><c:out value="${t.employeeServicer}" /></td>
                                        <td><c:out value="${t.servicer.id}" /> <c:out value="${t.servicer.person.fullName}" /></td>
                                        <td><c:out value="${t.errors}" /></td>
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

<%--<div class="modal fade" id="upload-modal" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title"></h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="form-group form-group-last">
                    <div class="alert alert-secondary" role="alert">
                        <div class="alert-icon"><i class="flaticon-warning kt-font-brand"></i></div>
                        <div class="alert-text">
                            Yüklənmə Excel faylından nəzərdə tutulmuşdur. [.xlsx] formatlı fayldan məlumatı yükləyə bilərsiniz.
                            Şablon excel faylı formasını <a href="<c:url value="/assets/template/non-working-day-example.xlsx" />" target="_blank">buradan endirə</a> bilərsiniz.
                        </div>
                    </div>
                </div>
                <form:form modelAttribute="form" id="upload-form" method="post" action="/admin/migration/upload" cssClass="form-group" enctype="multipart/form-data">
                    <form:input type="hidden" name="id" path="id"/>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <form:label path="organization">Struktur</form:label>
                                <form:select  path="organization" cssClass="custom-select form-control">
                                    <form:option value=""/>
                                    <form:options items="${organizations}" itemLabel="name" itemValue="id" />
                                </form:select>
                                <form:errors path="organization" cssClass="control-label alert-danger" />
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <form:label path="operationType">Əməliyyatın tipi</form:label>
                                <form:select  path="operationType" cssClass="custom-select form-control">
                                    <form:option value=""/>
                                    <form:option value="Satış" label="Satış"/>
                                    <form:option value="Qaytarılma" label="Qaytarılma"/>
                                    <form:option value="Servis Yenilənməsi" label="Servis Yenilənməsi"/>
                                </form:select>
                                <form:errors path="operationType" cssClass="control-label alert-danger" />
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label>Baza excel faylı</label>
                        <div></div>
                        <div class="custom-file">
                            <input type="file" name="file" class="custom-file-input" id="file" accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet">
                            <label class="custom-file-label" for="file">Baza faylını seçin</label>
                        </div>
                    </div>
                </form:form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="submit($('#upload-form'));">Yüklə</button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Bağla</button>
            </div>
        </div>
    </div>
</div>--%>

<script>
    $('.custom-file-input').on('change', function() {
        var fileName = $(this).val();
        $(this).next('.custom-file-label').addClass("selected").html(fileName);
    });

    $("#datatable").DataTable({
        responsive: true,
fixedHeader: {
   headerOffset: $('#kt_header').outerHeight()
},
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

    $( "#upload-form" ).validate({
        rules: {
            organization: {
                required: true
            },
            operationType: {
                required: true
            }
        },
        invalidHandler: function(event, validator) {
            KTUtil.scrollTop();
            swal.close();
        },
    })

    function migrationReload(id) {
        $("#migration-reload-form").find("input[name='id']").val(id);
        submit($("#migration-reload-form"));
    }
</script>