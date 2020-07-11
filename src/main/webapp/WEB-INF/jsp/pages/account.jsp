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
                            <c:set var="transfer" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'transfer')}"/>
                            <c:set var="edit" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'edit')}"/>
                            <c:set var="delete" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'delete')}"/>
                            <table class="table table-striped- table-bordered table-hover table-checkable" id="group_table">
                                <thead>
                                <tr>
                                    <th>№</th>
                                    <th>ID</th>
                                    <th></th>
                                    <th>Hesab nömrəsi</th>
                                    <th>Valyuta</th>
                                    <th>Bank</th>
                                    <th>Bank hesab nömrəsi</th>
                                    <th>Bank kodu</th>
                                    <th>Bank swift bik</th>
                                    <th>Balans</th>
                                    <th>Açıqlama</th>
                                    <th>Əməliyyat</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="t" items="${list}" varStatus="loop">
                                    <tr data="<c:out value="${utl:toJson(t)}" />">
                                        <td>${loop.index + 1}</td>
                                        <td><c:out value="${t.id}" /></td>
                                        <td><c:out value="${t.organization.name}"/></td>
                                        <th><c:out value="${t.accountNumber}"/></th>
                                        <td><c:out value="${t.currency}"/></td>
                                        <td><c:out value="${t.bankName}"/></td>
                                        <td><c:out value="${t.bankAccountNumber}"/></td>
                                        <td><c:out value="${t.bankCode}"/></td>
                                        <td><c:out value="${t.bankSwiftBic}"/></td>
                                        <th><c:out value="${t.balance}"/></th>
                                        <td><c:out value="${t.description}"/></td>
                                        <td nowrap class="text-center">
                                            <c:if test="${view.status}">
                                                <a href="javascript:view($('#form'), '<c:out value="${utl:toJson(t)}" />', 'modal-operation', '<c:out value="${view.object.name}" />');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${view.object.name}"/>">
                                                    <i class="<c:out value="${view.object.icon}"/>"></i>
                                                </a>
                                            </c:if>
                                            <c:if test="${transfer.status}">
                                                <a href="javascript:edit($('#transfer-form'), '<c:out value="${utl:toJson(t)}" />', 'transfer-modal-operation', '<c:out value="${transfer.object.name}" />');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${transfer.object.name}"/>">
                                                    <i class="<c:out value="${transfer.object.icon}"/>"></i>
                                                </a>
                                            </c:if>
                                            <c:if test="${edit.status}">
                                                <a href="javascript:edit($('#form'), '<c:out value="${utl:toJson(t)}" />', 'modal-operation', '<c:out value="${edit.object.name}" />');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${edit.object.name}"/>">
                                                    <i class="<c:out value="${edit.object.icon}"/>"></i>
                                                </a>
                                            </c:if>
                                            <c:if test="${delete.status}">
                                                <a href="javascript:deleteData('<c:out value="${t.id}" />', '<c:out value="${t.accountNumber}" /><br/><c:out value="${t.description}" />');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${delete.object.name}"/>">
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
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Yeni sorğu yarat</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form:form modelAttribute="form" id="form" method="post" action="/accounting/account" cssClass="form-group">
                    <form:hidden path="id"/>
                    <form:hidden path="organization"/>
                    <form:hidden path="active"/>
                    <div class="row">
                        <div class="col-9">
                            <div class="form-group">
                                <form:label path="accountNumber">Hesab nömrəsi</form:label>
                                <div class="input-group" >
                                    <div class="input-group-prepend"><span class="input-group-text"><i class="la la-bank"></i></span></div>
                                    <form:input path="accountNumber" cssClass="form-control account-number" placeholder="Hesab nömrəsi" readonly="true"/>
                                </div>
                                <form:errors path="accountNumber" cssClass="alert-danger control-label"/>
                            </div>
                        </div>
                        <div class="col-3">
                            <div class="form-group typeahead">
                                <form:label path="currency">Valyuta</form:label>
                                    <form:input path="currency" cssClass="form-control account-number2 type-ahead-currency" />
                                <form:errors path="currency" cssClass="alert-danger control-label"/>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <form:label path="description">Açıqlama</form:label>
                        <form:textarea path="description" cssClass="form-control" placeholder="Açıqlama daxil edin"/>
                        <form:errors path="description" cssClass="alert-danger control-label"/>
                    </div>
                    <div class="form-group">
                        <form:label path="balance">Balans</form:label>
                        <div class="input-group" >
                            <div class="input-group-prepend"><span class="input-group-text"><i class="la la-usd"></i></span></div>
                            <form:input path="balance" cssClass="form-control" placeholder="Balansı daxil edin"/>
                        </div>
                        <form:errors path="balance" cssClass="alert-danger control-label"/>
                    </div>
                    <div class="form-group">
                        <form:label path="bankName">Bank</form:label>
                        <form:input path="bankName" cssClass="form-control" placeholder="Bankınızı daxil edin. Məs: PashaBank"/>
                        <form:errors path="bankName" cssClass="alert-danger control-label"/>
                    </div>
                    <div class="form-group">
                        <form:label path="bankAccountNumber">Bank hesab nömrəsi</form:label>
                        <form:input path="bankAccountNumber" cssClass="form-control uppercase" placeholder="Bank hesab nömrəsini daxil edin"/>
                        <form:errors path="bankAccountNumber" cssClass="alert-danger control-label"/>
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <form:label path="bankCode">Bank kodu</form:label>
                                <form:input path="bankCode" cssClass="form-control uppercase" placeholder="Bank kodunu daxil edin" />
                                <form:errors path="bankCode" cssClass="alert-danger control-label"/>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <form:label path="bankSwiftBic">Bank swift bik</form:label>
                                <form:input path="bankSwiftBic" cssClass="form-control uppercase" placeholder="Bank swiftini daxil edin" />
                                <form:errors path="bankSwiftBic" cssClass="alert-danger control-label"/>
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

<div class="modal fade" id="transfer-modal-operation" tabindex="-1" role="dialog">
    <div class="modal-dialog modal-sm" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Yeni sorğu yarat</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form:form modelAttribute="transfer_form" id="transfer-form" method="post" action="/accounting/account/transfer" cssClass="form-group">
                    <form:hidden path="id"/>
                    <div class="form-group">
                        <form:label path="accountNumber">Hesabdan</form:label>
                        <div class="input-group" >
                            <div class="input-group-prepend"><span class="input-group-text"><i class="la la-bank"></i></span></div>
                            <form:input path="accountNumber" cssClass="form-control account-number2" placeholder="Hesab nömrəsi"/>
                        </div>
                        <form:errors path="accountNumber" cssClass="alert-danger control-label"/>
                    </div>
                    <%--<div class="form-group">
                        <form:label path="toAccountNumber">Hesaba</form:label>
                        <div class="input-group" >
                            <div class="input-group-prepend"><span class="input-group-text"><i class="la la-bank"></i></span></div>
                            <form:input path="toAccountNumber" cssClass="form-control account-number2" placeholder="Göndərilən hesab" onchange="getCurrency($('#transfer-form'), $(this))"/>
                        </div>
                        <form:errors path="toAccountNumber" cssClass="alert-danger control-label"/>
                    </div>--%>
                    <div class="form-group">
                        <form:label path="toAccountNumber">Hesaba</form:label>
                        <div class="input-group" >
                            <div class="input-group-prepend"><span class="input-group-text"><i class="la la-bank"></i></span></div>
                            <form:select  path="toAccountNumber" cssClass="custom-select form-control select2-single account-number3" multiple="single" onchange="getCurrency($('#transfer-form'), $(this))">
                                <form:option value=""></form:option>
                                <c:forEach var="itemGroup" items="${accounts}" varStatus="itemGroupIndex">
                                    <optgroup label="${itemGroup.key}">
                                        <form:options items="${itemGroup.value}" itemLabel="accountNumberWithCurrency2" itemValue="accountNumber"/>
                                    </optgroup>
                                </c:forEach>
                            </form:select>
                        </div>
                        <form:errors path="toAccountNumber" cssClass="alert-danger control-label"/>
                    </div>
                    <div class="row">
                        <div class="col-5">
                            <div class="form-group">
                                <form:label path="currency">Valyuta</form:label>
                                <div class="input-group" >
                                    <div class="input-group-prepend"><span class="input-group-text"><i class="la la-cube"></i></span></div>
                                    <form:input path="currency" cssClass="form-control" readonly="true"/>
                                </div>
                                <form:errors path="currency" cssClass="alert-danger control-label"/>
                            </div>
                        </div>
                        <div class="col-2 text-center pt-4">
                            <i class="la la-exchange kt-font-brand" style="font-size: 20px; margin-top: 10px;"></i>
                        </div>
                        <div class="col-5">
                            <div class="form-group">
                                <form:label path="toCurrency">Valyuta</form:label>
                                <div class="input-group" >
                                    <div class="input-group-prepend"><span class="input-group-text"><i class="la la-cube"></i></span></div>
                                    <form:input path="toCurrency" cssClass="form-control" readonly="true"/>
                                </div>
                                <form:errors path="toCurrency" cssClass="alert-danger control-label"/>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <form:label path="balance">Məbləğ</form:label>
                        <div class="input-group" >
                            <div class="input-group-prepend"><span class="input-group-text"><i class="la la-usd"></i></span></div>
                            <form:input path="balance" cssClass="form-control" placeholder="Balansı daxil edin"/>
                        </div>
                        <form:errors path="balance" cssClass="alert-danger control-label"/>
                    </div>
                    <div class="form-group">
                        <form:label path="description">Açıqlama</form:label>
                        <form:textarea path="description" cssClass="form-control" placeholder="Açıqlama daxil edin"/>
                        <form:errors path="description" cssClass="alert-danger control-label"/>
                    </div>
                </form:form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="submit($('#transfer-form'));">Yadda saxla</button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Bağla</button>
            </div>
        </div>
    </div>
</div>

<script src="<c:url value="/assets/vendors/general/typeahead.js/dist/typeahead.bundle.js" />" type="text/javascript"></script>
<script src="<c:url value="/assets/vendors/general/typeahead.js/dist/typeahead.jquery.js" />" type="text/javascript"></script>


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

    $('#group_table tbody').on('dblclick', 'tr', function () {
        <c:if test="${edit.status}">
        edit($('#form'), $(this).attr('data'), 'modal-operation', '<c:out value="${edit.object.name}" />');
        </c:if>
        <c:if test="${!edit.status and view.status}">
        view($('#form'), $(this).attr('data'), 'modal-operation', '<c:out value="${view.object.name}" />');
        </c:if>
    });

    $( "#form" ).validate({
        rules: {
            accountNumber: {
                required: true
            },
            currency: {
                required: true
            },
            balance: {
                required: true,
                number: true
            }
        },
        invalidHandler: function(event, validator) {
            KTUtil.scrollTop();
            swal.close();
        },
    });

    $( "#transfer-form" ).validate({
        rules: {
            accountNumber: {
                required: true
            },
            toAccountNumber: {
                required: true,
                notEqualTo: "#accountNumber"
            },
            balance: {
                required: true,
                number: true,
                min: 0.1
            }
        },
        invalidHandler: function(event, validator) {
            KTUtil.scrollTop();
            swal.close();
        },
    });

    $("input[name='balance']").inputmask('decimal', {
        rightAlignNumerics: false
    });

    var KTTypeahead = function() {

        var states = [
            <c:forEach var="t" items="${currencies}" varStatus="loop">
            '<c:out value="${t.name}"/>',
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

            $(".type-ahead-currency").typeahead({
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

    KTTypeahead.init();

    function getCurrency(form, account){
        $.ajax({
            url: '/accounting/api/account/'+$(account).val().trim(),
            type: 'GET',
            dataType: 'json',
            beforeSend: function() {
                $(form).find("input[name='toCurrency']").val('');
            },
            success: function(data) {
                $(form).find("input[name='toCurrency']").val(data.currency);
            }
        })
    }
</script>