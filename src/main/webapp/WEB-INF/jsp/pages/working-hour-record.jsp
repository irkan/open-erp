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
                    <form:hidden path="id"/>
                <c:set var="search" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'search')}"/>
                <c:choose>
                    <c:when test="${search.status}">
                        <div class="kt-portlet__head kt-portlet__head--lg">
                            <div class="kt-portlet__head-title" style="width: 100%">
                                <div class="row">
                                    <div class="col-sm-3 offset-sm-1">
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
                                    <div class="col-sm-3">
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
                                                Axtar
                                            </a>
                                            <button type="reset" class="btn btn-secondary btn-secondary--icon">
                                                <i class="la la-close"></i>
                                                Təmizlə
                                            </button>
                                        </div>
                                    </div>
                                    <div class="col-sm-2">
                                        <label>&nbsp;</label>
                                        <div class="form-group">
                                            <a href="#" onclick="submit($('#form'))" class="btn btn-warning btn-elevate btn-icon-sm" title="<c:out value="${search.object.name}"/>">
                                                <i class="la <c:out value="${search.object.icon}"/>"></i>
                                                Yadda Saxla
                                            </a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:when>
                </c:choose>
                <div class="kt-portlet__body">

                    <c:choose>
                        <c:when test="${not empty form.workingHourRecordEmployees}">
                            <table class="table table-striped- table-bordered table-hover table-checkable"
                                   id="kt_table_3">
                                <thead>
                                <tr>
                                    <th rowspan="2">№</th>
                                    <th rowspan="2"><div style="width: 220px !important;">Ad Soyad Ata adı</div></th>
                                    <th colspan="2" class="text-center" style="letter-spacing: 4px;">Əməkdaş</th>
                                    <th colspan="<c:out value="${days_in_month}"/>" class="text-center" style="letter-spacing: 4px;">
                                        Ayın günləri
                                    </th>
                                </tr>
                                <tr>
                                    <th class="bg-warning" style="border: none;"><div style="width: 180px !important;">Vəzifə</div></th>
                                    <th class="bg-warning" style="border: none;"><div style="width: 120px !important;">Struktur</div></th>
                                    <c:forEach var = "i" begin = "1" end = "${days_in_month}">
                                        <th class="bg-info text-center kt-padding-0" style="border: none; color: white"><c:out value = "${i}"/></th>
                                    </c:forEach>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="t" items="${form.workingHourRecordEmployees}" varStatus="loop">
                                    <tr>
                                        <td class="text-center">
                                            ${loop.index + 1}
                                            <input type="hidden" name="id" value="<c:out value="${t.id}"/>"/>
                                            <input type="hidden" name="employee-id" value="<c:out value="${t.id}"/>"/>
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
                                        <c:forEach var = "i" begin = "1" end = "${days_in_month}">
                                            <td class="text-center kt-padding-0">
                                                <div class="typeahead">
                                                    <input type="text" name="identify" class="type-ahead" value="İG">
                                                </div>
                                            </td>
                                        </c:forEach>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </c:when>
                        <c:otherwise>
                            Məlumat yoxdur
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
</script>


