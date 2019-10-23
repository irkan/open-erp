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
                <form:form modelAttribute="form" id="form" method="post" action="/payroll/salary/calculate" cssClass="form-group">
                    <input name="id" type="hidden" value="<c:out value="${form.id}"/>" />
                    <input name="workingHourRecord.id" type="hidden" value="<c:out value="${form.workingHourRecord.id}"/>" />
                <c:set var="calculate" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'calculate')}"/>
                <c:choose>
                    <c:when test="${calculate.status}">
                        <div class="kt-portlet__head kt-portlet__head--lg">
                            <div class="kt-portlet__head-title" style="width: 100%">
                                <div class="row">
                                    <div class="col-sm-3">
                                        <div class="form-group">
                                            <form:label path="workingHourRecord.branch">&nbsp;</form:label>
                                            <form:select  path="workingHourRecord.branch" cssClass="custom-select form-control">
                                                <c:forEach var="t" items="${branches}" varStatus="loop">
                                                    <c:set var="selected" value="${t.id==form.workingHourRecord.branch.id?'selected':''}"/>
                                                    <option value="<c:out value="${t.id}"/>" <c:out value="${selected}"/>><c:out value="${t.name}"/></option>
                                                </c:forEach>
                                            </form:select>
                                            <form:errors path="workingHourRecord.branch" cssClass="control-label alert alert-danger" />
                                        </div>
                                    </div>
                                    <div class="col-sm-2">
                                        <div class="form-group">
                                            <label>&nbsp;</label>
                                            <input name="workingHourRecord.monthYear" class="form-control" type="month" value="<c:out value="${form.workingHourRecord.monthYear}"/>" />
                                        </div>
                                    </div>
                                    <div class="col-sm-3">
                                        <label>&nbsp;</label>
                                        <div class="form-group">
                                            <a href="#" onclick="submit($('#form'))" class="btn btn-brand btn-elevate btn-icon-sm" title="<c:out value="${calculate.object.name}"/>">
                                                <i class="la <c:out value="${calculate.object.icon}"/>"></i>
                                                <c:out value="${calculate.object.name}"/>
                                            </a>
                                            <button type="reset" class="btn btn-secondary btn-secondary--icon">
                                                <i class="la la-close"></i>
                                                Təmizlə
                                            </button>
                                        </div>
                                    </div>
                                    <div class="col-sm-4 text-right">
                                        <label>&nbsp;</label>
                                        <div class="form-group">
                                            <c:set var="save" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'save')}"/>
                                            <c:choose>
                                                <c:when test="${save.status}">
                                                    <c:if test="${not empty form}">
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
                                                    <c:if test="${not empty form and !form.approve}">
                                                        <a href="#" onclick="saveWHR($('#form'))" class="btn btn-success btn-elevate btn-icon-sm" title="<c:out value="${approve.object.name}"/>">
                                                            <i class="la <c:out value="${approve.object.icon}"/>"></i>
                                                            <c:out value="${approve.object.name}"/>
                                                        </a>
                                                    </c:if>
                                                </c:when>
                                            </c:choose>
                                            <c:set var="cancel" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'cancel')}"/>
                                            <c:choose>
                                                <c:when test="${cancel.status}">
                                                    <c:if test="${not empty form and !form.approve}">
                                                        <a href="#" onclick="saveWHR($('#form'))" class="btn btn-dark btn-elevate btn-icon-sm" title="<c:out value="${cancel.object.name}"/>">
                                                            <i class="la <c:out value="${cancel.object.icon}"/>"></i>
                                                            <c:out value="${cancel.object.name}"/>
                                                        </a>
                                                    </c:if>
                                                </c:when>
                                            </c:choose>
                                            <c:set var="delete" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'delete')}"/>
                                            <c:choose>
                                                <c:when test="${delete.status}">
                                                    <c:if test="${not empty form}">
                                                        <a href="javascript:deleteData('<c:out value="${form.workingHourRecord.id}" />', '<c:out value="${form.workingHourRecord.month}"/>.<c:out value="${form.workingHourRecord.year}"/> tarixli maaş hesablanması ');" class="btn btn-danger btn-elevate btn-icon-sm" title="<c:out value="${delete.object.name}"/>">
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
                                <c:forEach var="t" items="${form.salaryEmployees}" varStatus="loop">
                                    <c:forEach var="p" items="${t.salaryEmployeeDetails}" varStatus="loop">
                                        <tr>
                                            <td><c:out value="${p.id}" /></td>
                                            <td><c:out value="${p.skeletonFormula}" /> = <c:out value="${p.formula}" /></td>
                                            <td><c:out value="${t.workingHourRecordEmployee.fullName}" /></td>
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
                </form:form>
            </div>
        </div>

    </div>

</div>

<div class="modal fade" id="modal-operation" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title"></h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <%--<form:form modelAttribute="form" id="form" method="post" action="/admin/dictionary-type"
                           cssClass="form-group">
                    <form:input type="hidden" name="id" path="id"/>
                    <form:input type="hidden" name="active" path="active" value="1"/>
                    <div class="form-group">
                        <form:label path="name">Ad</form:label>
                        <form:input path="name" cssClass="form-control" placeholder="Adı daxil edin"/>
                        <form:errors path="name" cssClass="alert-danger control-label"/>
                    </div>
                    <div class="form-group">
                        <form:label path="attr1">Atribut#1</form:label>
                        <form:input path="attr1" cssClass="form-control" placeholder="Atributu daxil edin"/>
                        <form:errors path="attr1" cssClass="alert-danger"/>
                    </div>
                    <div class="form-group">
                        <form:label path="attr2">Atribut#2</form:label>
                        <form:input path="attr2" cssClass="form-control" placeholder="Atributu daxil edin"/>
                        <form:errors path="attr2" cssClass="alert alert-danger"/>
                    </div>
                </form:form>--%>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="submit($('#form'));">Yadda saxla</button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Bağla</button>
            </div>
        </div>
    </div>
</div>

<script src="<c:url value="/assets/vendors/general/typeahead.js/dist/typeahead.bundle.js" />" type="text/javascript"></script>
<script src="<c:url value="/assets/vendors/general/typeahead.js/dist/typeahead.jquery.js" />" type="text/javascript"></script>
<script src="<c:url value="/assets/js/demo4/pages/crud/datatables/advanced/row-grouping.js" />" type="text/javascript"></script>

<script>
    var KTTypeahead = function() {

        var states = [
            <c:forEach var="t" items="${identifiers}" varStatus="loop">
                '<c:out value="${t.attr1}"/>',
            </c:forEach>
        ];

        // Private functions
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
                    {orderable: false, targets: 0}/*,
                    {class: 'col-2', targets: 1},
                    {width: '400px', class: 'col-2', targets: 2},
                    {orderable: false, targets: 3}*/
                ],
                /*aoColumns : [ { "sClass": "my_class" }],*/
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
</script>


