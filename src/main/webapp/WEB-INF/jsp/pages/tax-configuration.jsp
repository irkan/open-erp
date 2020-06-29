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
                            <c:set var="edit" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'edit')}"/>
                            <c:set var="delete" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'delete')}"/>
                            <c:set var="view" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'view')}"/>
                            <c:set var="export" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'export')}"/>
                            <table class="table table-striped- table-bordered table-hover table-checkable" id="group_table">
                                <thead>
                                <tr>
                                    <th>№</th>
                                    <th>ID</th>
                                    <th>Struktur</th>
                                    <th>VÖEN</th>
                                    <th>Şirkət</th>
                                    <th>Açıqlama</th>
                                    <th>Əlaqəli şəxs</th>
                                    <th>Email</th>
                                    <th>Mobil nömrə</th>
                                    <th>Ofis nömrəsi</th>
                                    <th>Ünvan</th>
                                    <th>Aylıq limit</th>
                                    <th>Satış sayı</th>
                                    <th style="max-width: 70px">Planlaşdırılmış ödəniş</th>
                                    <th>Əməliyyat</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="t" items="${list}" varStatus="loop">
                                    <tr data="<c:out value="${utl:toJson(t)}" />">
                                        <td>${loop.index + 1}</td>
                                        <td><c:out value="${t.id}" /></td>
                                        <td><c:out value="${t.organization.name}" /></td>
                                        <td><c:out value="${t.voen}" /></td>
                                        <td><c:out value="${t.company}" /></td>
                                        <td><c:out value="${t.description}" /></td>
                                        <td><c:out value="${t.person.fullName}" /></td>
                                        <td><c:out value="${t.person.contact.email}" /></td>
                                        <td><c:out value="${t.person.contact.mobilePhone}" /></td>
                                        <td><c:out value="${t.person.contact.homePhone}" /></td>
                                        <td><c:out value="${t.person.contact.city.name}" />, <c:out value="${t.person.contact.address}" /></td>
                                        <td><c:out value="${t.maxLimitMonthly}" /> AZN</td>
                                        <td><c:out value="${t.salesCount}" /></td>
                                        <td><c:out value="${t.plannedPaymentAmountMonthly}" /> AZN</td>
                                        <td nowrap class="text-center">
                                            <c:if test="${view.status}">
                                                <a href="javascript:view($('#form'), '<c:out value="${utl:toJson(t)}" />', 'modal-operation', '<c:out value="${view.object.name}" />');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${view.object.name}"/>">
                                                    <i class="<c:out value="${view.object.icon}"/>"></i>
                                                </a>
                                            </c:if>
                                            <c:if test="${edit.status}">
                                                <a href="javascript:edit($('#form'), '<c:out value="${utl:toJson(t)}" />', 'modal-operation', '<c:out value="${edit.object.name}" />');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${edit.object.name}"/>">
                                                    <i class="<c:out value="${edit.object.icon}"/>"></i>
                                                </a>
                                            </c:if>
                                            <c:if test="${delete.status}">
                                                <a href="javascript:deleteData('<c:out value="${t.id}" />', '<c:out value="${t.company}" />');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${delete.object.name}"/>">
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
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Yarat</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form:form modelAttribute="form" id="form" method="post" action="/admin/tax-configuration" cssClass="form-group">
                    <form:input path="id" type="hidden"/>
                    <form:input path="organization" type="hidden"/>
                    <div class="row">
                        <div class="col-md-3">
                            <div class="form-group">
                                <form:label path="voen">VÖEN</form:label>
                                <form:input path="voen" cssClass="form-control account-number3 uppercase" placeholder="Daxil edin" maxlength="10"/>
                                <form:errors path="voen" cssClass="control-label alert-danger" />
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <form:label path="company">Şirkət adı</form:label>
                                <form:input path="company" cssClass="form-control" placeholder="Daxil edin"/>
                                <form:errors path="company" cssClass="control-label alert-danger" />
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <form:label path="description">Açıqlama</form:label>
                                <form:input path="description" cssClass="form-control" placeholder="Açıqlama daxil edin"/>
                                <form:errors path="description" cssClass="control-label alert-danger" />
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <form:label path="maxLimitMonthly">Aylıq limit</form:label>
                                <div class="input-group" >
                                    <div class="input-group-prepend"><span class="input-group-text"><i class="la la-usd"></i></span></div>
                                    <form:input path="maxLimitMonthly" cssClass="form-control" placeholder="Daxil edin"/>
                                </div>
                                <form:errors path="maxLimitMonthly" cssClass="control-label alert-danger"/>
                            </div>
                        </div>
                    </div>
                    <hr style="width: 100%"/>
                    <div class="row">
                        <div class="col-md-4">
                            <div class="form-group">
                                <form:label path="person.firstName">Ad</form:label>
                                <form:input path="person.firstName" cssClass="form-control" placeholder="Adı daxil edin Məs: Səbuhi"/>
                                <form:errors path="person.firstName" cssClass="control-label alert-danger" />
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-group">
                                <form:label path="person.lastName">Soyad</form:label>
                                <form:input path="person.lastName" cssClass="form-control" placeholder="Soyadı daxil edin Məs: Vəliyev"/>
                                <form:errors path="person.lastName" cssClass="control-label alert-danger" />
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-group">
                                <form:label path="person.fatherName">Ata adı</form:label>
                                <form:input path="person.fatherName" cssClass="form-control" placeholder="Ata adını daxil edin"/>
                            </div>
                        </div>
                    </div>
                    <hr style="width: 100%"/>
                    <div class="row">
                        <div class="col-md-4">
                            <div class="form-group">
                                <form:label path="person.contact.email">Email</form:label>
                                <div class="input-group">
                                    <div class="input-group-prepend"><span class="input-group-text"><i class="la la-at"></i></span></div>
                                    <form:input path="person.contact.email" cssClass="form-control" placeholder="example@example.com"/>
                                </div>
                                <form:errors path="person.contact.email" cssClass="control-label alert-danger" />
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-group">
                                <form:label path="person.contact.mobilePhone">Mobil nömrə</form:label>
                                <div class="input-group" >
                                    <div class="input-group-prepend"><span class="input-group-text"><i class="la la-phone"></i></span></div>
                                    <form:input path="person.contact.mobilePhone" cssClass="form-control" placeholder="505505550"/>
                                </div>
                                <form:errors path="person.contact.mobilePhone" cssClass="control-label alert-danger" />
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-group">
                                <form:label path="person.contact.homePhone">Şəhər nömrəsi</form:label>
                                <div class="input-group">
                                    <div class="input-group-prepend"><span class="input-group-text"><i class="la la-phone"></i></span></div>
                                    <form:input path="person.contact.homePhone" cssClass="form-control" placeholder="124555050"/>
                                </div>
                                <form:errors path="person.contact.homePhone" cssClass="control-label alert-danger" />
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-4">
                            <div class="form-group">
                                <form:label path="person.contact.city">Şəhər</form:label>
                                <form:select  path="person.contact.city" cssClass="custom-select form-control">
                                    <form:option value=""></form:option>
                                    <form:options items="${cities}" itemLabel="name" itemValue="id" />
                                </form:select>
                            </div>
                        </div>
                        <div class="col-md-8">
                            <div class="form-group">
                                <form:label path="person.contact.address">Ünvan</form:label>
                                <div class="input-group" >
                                    <div class="input-group-prepend"><span class="input-group-text"><i class="la la-street-view"></i></span></div>
                                    <form:input path="person.contact.address" cssClass="form-control" placeholder="Küçə adı, ev nömrəsi və s."/>
                                </div>
                                <form:errors path="person.contact.address" cssClass="control-label alert-danger" />
                            </div>
                        </div>
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
    $('#group_table').DataTable({
        <c:if test="${export.status}">
        dom: 'B<"clear">lfrtip',
        buttons: [
            'copy', 'csv', 'excel', 'pdf', 'print'
        ],
        </c:if>
        responsive: true,
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

    $('#group_table tbody').on('dblclick', 'tr', function () {
        <c:if test="${view.status}">
        view($('#form'), $(this).attr('data'), 'modal-operation', '<c:out value="${view.object.name}" />');
        </c:if>
    });

    $( "#form" ).validate({
        rules: {
            voen: {
                required: true,
                minlength: 3,
                maxlength: 10
            },
            maxLimitMonthly: {
                required: true,
                number: true,
                min: 1
            },
            description: {
                maxlength: 80,
            },
            "person.firstName": {
                required: true
            },
            "person.lastName": {
                required: true
            },
            "person.contact.mobilePhone": {
                required: true
            },
            "person.contact.city": {
                required: true
            },
            "person.contact.address": {
                required: true
            }
        },
        invalidHandler: function(event, validator) {
                    KTUtil.scrollTop();
            swal.close();
        },
    });

    $("input[name='person.contact.email']").inputmask({
        mask: "*{1,20}[.*{1,20}][.*{1,20}][.*{1,20}]@*{1,20}[.*{2,6}][.*{1,2}]",
        greedy: false,
        onBeforePaste: function (pastedValue, opts) {
            pastedValue = pastedValue.toLowerCase();
            return pastedValue.replace("mailto:", "");
        },
        definitions: {
            '*': {
                validator: "[0-9A-Za-z!#$%&'*+/=?^_`{|}~\-]",
                cardinality: 1,
                casing: "lower"
            }
        }
    });

    $("input[name='person.contact.mobilePhone']").inputmask("mask", {
        "mask": "(999) 999-9999"
    });

    $("input[name='person.contact.homePhone']").inputmask("mask", {
        "mask": "(999) 999-9999"
    });
    $("input[name='maxLimitMonthly']").inputmask('decimal', {
        rightAlignNumerics: false
    });
</script>




