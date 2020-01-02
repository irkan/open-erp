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
<link href="<c:url value="/assets/css/demo4/pages/wizard/wizard-1.css" />" rel="stylesheet" type="text/css"/>
<div class="kt-container  kt-grid__item kt-grid__item--fluid">
    <div class="row">
        <div class="col-lg-12">
            <div class="kt-portlet kt-portlet--mobile">
                <div class="kt-portlet__body">
                    <c:choose>
                        <c:when test="${not empty list}">
                            <c:set var="view" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'view')}"/>
                            <c:set var="detail" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'detail')}"/>
                            <c:set var="edit" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'edit')}"/>
                            <c:set var="delete" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'delete')}"/>
                            <c:set var="export" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'export')}"/>
                            <table class="table table-striped- table-bordered table-hover table-checkable" id="group_table">
                                <thead>
                                <tr>
                                    <th>Əməliyyat</th>
                                    <th>Kod</th>
                                    <th>Satış tarixi</th>
                                    <th>İnventar</th>
                                    <th>Müştəri</th>
                                    <th>Qiymət</th>
                                    <th>Qrafik</th>
                                    <th>Ödəniş</th>
                                    <th>Satış komandası</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="t" items="${list}" varStatus="loop">
                                    <tr data="<c:out value="${utl:toJson(t)}" />">
                                        <td nowrap class="text-center">
                                            <span class="dropdown">
                                                <a href="#" class="btn btn-sm btn-clean btn-icon btn-icon-md" data-toggle="dropdown" aria-expanded="true">
                                                  <i class="la la-ellipsis-h"></i>
                                                </a>
                                                <div class="dropdown-menu dropdown-menu-right">
                                                    <c:if test="${view.status}">
                                                    <a href="/collect/payment-regulator-note/<c:out value="${t.payment.id}"/>" class="dropdown-item" title="Qeydlər">
                                                        <i class="la <c:out value="${view.object.icon}"/>"></i> Qeydlər
                                                    </a>
                                                    </c:if>
                                                    <c:if test="${detail.status}">
                                                    <a href="/collect/payment-regulator-detail/<c:out value="${t.payment.id}"/>" class="dropdown-item" title="<c:out value="${detail.object.name}"/>">
                                                        <i class="la <c:out value="${detail.object.icon}"/>"></i> <c:out value="${detail.object.name}"/>
                                                    </a>
                                                    </c:if>
                                                    <c:if test="${edit.status}">
                                                    <a href="javascript:edit($('#kt_form'), '<c:out value="${utl:toJson(t)}" />', 'modal-operation', '<c:out value="${edit.object.name}" />');" class="dropdown-item" title="<c:out value="${edit.object.name}"/>">
                                                        <i class="<c:out value="${edit.object.icon}"/>"></i> <c:out value="${edit.object.name}"/>
                                                    </a>
                                                    </c:if>
                                                    <c:if test="${delete.status}">
                                                    <a href="javascript:deleteData('<c:out value="${t.id}" />', '<c:out value="${t.salesInventories.get(0).inventory.name}" /> <br/> <c:out value="${t.customer.person.fullName}" />');" class="dropdown-item" title="<c:out value="${delete.object.name}"/>">
                                                        <i class="<c:out value="${delete.object.icon}"/>"></i> <c:out value="${delete.object.name}"/>
                                                    </a>
                                                    </c:if>
                                                </div>
                                            </span>
                                            <c:if test="${edit.status}">
                                                <a href="javascript:edit($('#kt_form'), '<c:out value="${utl:toJson(t)}" />', 'modal-operation', '<c:out value="${edit.object.name}" />');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${edit.object.name}"/>">
                                                    <i class="<c:out value="${edit.object.icon}"/>"></i>
                                                </a>
                                            </c:if>
                                        </td>
                                        <td><c:out value="${t.id}" /></td>
                                        <td><fmt:formatDate value = "${t.saleDate}" pattern = "dd.MM.yyyy" /></td>
                                        <th>
                                            <c:out value="${t.salesInventories.get(0).inventory.name}" /><br/>
                                            <c:out value="${t.salesInventories.get(0).inventory.barcode}" /><br/>
                                            <c:out value="${t.salesInventories.get(0).inventory.description}" />
                                        </th>
                                        <th>
                                            <c:out value="${t.customer.person.fullName}" /><br/>
                                            Müştəri kodu: <c:out value="${t.customer.id}" />
                                            <c:if test="${not empty t.customer.person.contact.email}">
                                                <c:out value="${t.customer.person.contact.email}" /><br/>
                                            </c:if>
                                            <c:if test="${not empty t.customer.person.contact.mobilePhone}">
                                                <c:out value="${t.customer.person.contact.mobilePhone}" />&nbsp;
                                            </c:if>
                                            <c:if test="${not empty t.customer.person.contact.homePhone}">
                                                <c:out value="${t.customer.person.contact.homePhone}" />
                                            </c:if>
                                            <c:if test="${not empty t.customer.person.contact.relationalPhoneNumber1}">
                                                <c:out value="${t.customer.person.contact.relationalPhoneNumber1}" />
                                            </c:if>
                                            <c:if test="${not empty t.customer.person.contact.relationalPhoneNumber2}">
                                                <c:out value="${t.customer.person.contact.relationalPhoneNumber2}" />
                                            </c:if>
                                            <c:if test="${not empty t.customer.person.contact.relationalPhoneNumber2}">
                                                <c:out value="${t.customer.person.contact.relationalPhoneNumber2}" />
                                            </c:if><br/>
                                            <c:if test="${not empty t.customer.person.contact.address}">
                                                <c:out value="${t.customer.person.contact.city.name}" />,&nbsp;
                                                <c:out value="${t.customer.person.contact.address}" /><br/>
                                            </c:if>
                                            <c:if test="${not empty t.customer.person.contact.livingAddress}">
                                                <c:out value="${t.customer.person.contact.livingCity.name}" />,&nbsp;
                                                <c:out value="${t.customer.person.contact.livingAddress}" />
                                            </c:if>
                                        </th>
                                        <th>
                                            Qiymət: <c:out value="${t.payment.price}" /><br/>
                                            <c:if test="${not empty t.payment.discount}">
                                                Endirim: <c:out value="${t.payment.discount}" /><br/>
                                            </c:if>
                                            <c:if test="${not empty t.payment.description}">
                                                Səbəbi: <c:out value="${t.payment.description}" /><br/>
                                            </c:if>
                                            <c:if test="${t.payment.down>0}">
                                                İlkin ödəniş: <c:out value="${t.payment.down}" /><br/>
                                            </c:if>
                                            Son qiymət: <c:out value="${t.payment.lastPrice}" />
                                        </th>
                                        <td>
                                            Qrafik: <c:out value="${t.payment.schedule.name}" /><br/>
                                            Period: <c:out value="${t.payment.period.name}" /><br/>
                                            Zəmanət müddəti: <c:out value="${t.guarantee}" /> ay<br/>
                                            Zəmanət bitir: <fmt:formatDate value = "${t.guaranteeExpire}" pattern = "dd.MM.yyyy" />
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${t.payment.cash}">
                                                    <span class="kt-font-bold kt-font-success">Nəğd</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="kt-font-bold kt-font-danger">Kredit</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            Konsul: <c:out value="${t.console.person.fullName}" /><br/>
                                            Ven lider: <c:out value="${t.vanLeader.person.fullName}" /><br/>
                                            Diller: <c:out value="${t.dealer.person.fullName}" /><br/>
                                            Canvasser: <c:out value="${t.canavasser.person.fullName}" />
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
                <h5 class="modal-title"></h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form:form modelAttribute="form" id="form" method="post" action="/sale/service" cssClass="form-group">
                    <form:hidden path="id"/>
                    <form:hidden path="active" value="1"/>
                    <input type="hidden" name="organization" value="<c:out value="${sessionScope.organization.id}"/>"/>
                    <div class="row">
                        <div class="col-md-4">
                            <div class="form-group">
                                <form:label path="customer">Müştəri kodu</form:label>
                                <div class="input-group">
                                    <form:input path="customer" autocomplete="false" class="form-control" placeholder="Müştəri kodu..."/>
                                    <div class="input-group-append">
                                        <button class="btn btn-primary" type="button" onclick="findCustomer($('input[name=\'customer\']'))">Müştəri axtar</button>
                                    </div>
                                </div>
                            </div>
                            <table id="customer-content" class="table table-striped- table-bordered table-hover table-checkable"></table>
                        </div>
                        <div class="col-md-5">
                            <div id="kt_repeater_1">
                                <div class="form-group form-group-last row" id="kt_repeater_2">
                                    <div data-repeater-list="" class="col-lg-12">
                                        <div data-repeater-item class="form-group row align-items-center">
                                            <div class="col-9">
                                                <div class="kt-form__group--inline">
                                                    <div class="kt-form__label">
                                                        <label>İnventar:</label>
                                                    </div>
                                                    <div class="kt-form__control">
                                                        <input type="text" name="barcode" class="form-control" placeholder="Barkodu daxil edin...">
                                                    </div>
                                                </div>
                                                <div class="d-md-none kt-margin-b-10"></div>
                                            </div>
                                            <div class="col-3">
                                                <label>&nbsp;</label>
                                                <div>
                                                    <a href="javascript:;" data-repeater-delete="" class="btn-sm btn btn-label-danger btn-bold">
                                                        <i class="la la-trash-o"></i>
                                                        Sil
                                                    </a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group form-group-last row">
                                    <div class="col-lg-6">
                                        <a href="javascript:;" data-repeater-create="" class="btn btn-bold btn-sm btn-label-primary">
                                            <i class="la la-plus"></i> Əlavə et
                                        </a>
                                    </div>
                                    <div class="col-lg-6 text-right">
                                        <a href="javascript:window.open('http://localhost:8080/warehouse/inventory', 'mywindow', 'width=1250, height=800')" data-repeater-delete="" class="btn-sm btn btn-label-success btn-bold">
                                            <i class="la la-search"></i>
                                            Axtar
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <form:label path="payment.price">Qiymət</form:label>
                                <form:input path="payment.price" cssClass="form-control" placeholder="Qiyməti daxil edin"/>
                                <form:errors path="payment.price" cssClass="alert-danger control-label"/>
                            </div>
                            <div class="form-group">
                                <form:label path="payment.discount">Endirim</form:label>
                                <form:input path="payment.discount" cssClass="form-control" placeholder="Endirimi daxil edin"/>
                                <form:errors path="payment.discount" cssClass="alert-danger control-label"/>
                            </div>
                            <div class="form-group">
                                <form:label path="payment.lastPrice">Son qiymət</form:label>
                                <form:input path="payment.lastPrice" cssClass="form-control" placeholder="Son qiyməti daxil edin" readonly="true"/>
                                <form:errors path="payment.lastPrice" cssClass="alert-danger control-label"/>
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

<form id="form-export-contract" method="post" action="/export/sale/contract" style="display: none">
    <input type="hidden" name="data" />
</form>

<script src="<c:url value="/assets/js/demo4/pages/crud/datatables/advanced/row-grouping.js" />" type="text/javascript"></script>

<script>
    $('.select2-single').select2({
        placeholder: "Əməkdaşı seçin",
        allowClear: true
    });

    function calculate(element){
        var price = $(element).val();
        var discount = $("input[name='payment.discount']").val();
        if(discount.trim().length>0){
            var discounts = discount.trim().split("%");
            if(discounts.length>1){
                price = price-price*parseFloat(discounts[0])*0.01;
            } else {
                price = price-parseFloat(discounts[0]);
            }
            price = Math.ceil(price);
        }
        $("#lastPriceLabel").text(price);
        $("input[name='payment.lastPrice']").val(price);
    }

    $(function(){
        $("select[name='payment.price']").change();
    });

    function doCash(element, defaultCash){
        if($(element).is(":checked")){
            $("#cash-div").removeClass("kt-hidden");
            $("#credit-div").addClass("kt-hidden");
            $("#schedule-div").html('');
            swal.fire({
                title: 'Endirimi təsdiq edirsinizmi?',
                html: 'Endirim faiz və ya məbləğini daxil edin',
                type: 'question',
                allowEnterKey: true,
                showCancelButton: true,
                buttonsStyling: false,
                cancelButtonText: 'Xeyr, edilməsin!',
                cancelButtonClass: 'btn btn-danger',
                confirmButtonText: 'Bəli, edilsin!',
                confirmButtonClass: 'btn btn-success',
                reverseButtons: true,
                allowOutsideClick: false,
                input: 'text',
                inputPlaceholder: 'Buraya daxil edin...',
                inputValue: defaultCash,
                inputAttributes: {
                    maxlength: 10,
                    autocapitalize: 'off',
                    autocorrect: 'off',
                    id: 'sale-value',
                    style: 'text-align: -webkit-center; text-align: center; font-weight: bold; letter-spacing: 3px;'
                },
                footer: '<a href>Məlumatlar yenilənsinmi?</a>'
            }).then(function(result) {
                $("input[name='payment.discount']").val('');
                $("input[name='payment.description']").val('');
                if (result.value) {
                    $("input[name='payment.discount']").val($('#sale-value').val());
                    $("select[name='payment.price']").change();
                    if($('#sale-value').val()!==defaultCash){
                        swal.fire({
                            title: $('#sale-value').val()+' - Səbəbini daxil edin',
                            type: 'info',
                            allowEnterKey: true,
                            buttonsStyling: false,
                            confirmButtonText: 'Təsdiq edirəm',
                            confirmButtonClass: 'btn btn-default',
                            allowOutsideClick: false,
                            input: 'textarea',
                            inputPlaceholder: 'Buraya daxil edin...',
                            inputAttributes: {
                                autocapitalize: 'off',
                                autocorrect: 'off',
                                style: 'letter-spacing: 1px;',
                                'aria-label': 'Type your message here'
                            },
                            footer: '<a href>Məlumatlar yenilənsinmi?</a>'
                        }).then(function(result2){
                            if(result2.value.length>0){
                                $("input[name='payment.description']").val(result2.value);
                            }
                        })

                    }
                }
            })

        } else {
            var price = $("select[name='payment.price']").val();
            $("#lastPriceLabel").text(price);
            $("input[name='payment.lastPrice']").val(price);
            $("#cash-div").addClass("kt-hidden");
            $("#credit-div").removeClass("kt-hidden");
        }
    }


    $('.custom-file-input').on('change', function() {
        var fileName = $(this).val();
        $(this).next('.custom-file-label').addClass("selected").html(fileName);
    });

    function findCustomer(element){
        var tr = '';
        if($(element).val().trim().length>0){
            swal.fire({
                text: 'Proses davam edir...',
                allowOutsideClick: false,
                onOpen: function() {
                    swal.showLoading();
                    $.ajax({
                        url: '/crm/customer/'+$(element).val(),
                        type: 'GET',
                        dataType: 'json',
                        beforeSend: function() {

                        },
                        success: function(customer) {
                            console.log(customer);
                            tr += '<tr class="text-center"><td class="text-center" colspan="2">'+customer.person.fullName+'</td></tr>';
                            tr += '<tr><td>Doğum tarixi</td><td>'+customer.person.birthday+'</td></tr>';
                            tr += '<tr><td>Seriya nömrəsi</td><td>'+customer.person.idCardSerialNumber+'</td></tr>';
                            tr += '<tr><td>Pin kodu</td><td>'+customer.person.idCardPinCode+'</td></tr>';
                            $('#customer-content').html(tr);
                            swal.close();
                        },
                        error: function() {
                            swal.fire({
                                title: "Müştəri tapılmadı!",
                                html: "Müştəri kodunun doğruluğunu yoxlayın.",
                                type: "error",
                                cancelButtonText: 'Bağla',
                                cancelButtonColor: '#c40000',
                                cancelButtonClass: 'btn btn-danger',
                                footer: '<a href>Məlumatlar yenilənsinmi?</a>'
                            });
                        }
                    })
                }
            });
        }
    }

    function findInventory(element){
        if($(element).val().trim().length>0){
            swal.fire({
                text: 'Proses davam edir...',
                allowOutsideClick: false,
                onOpen: function() {
                    swal.showLoading();
                    $.ajax({
                        url: '/warehouse/inventory/'+$(element).val(),
                        type: 'GET',
                        dataType: 'json',
                        beforeSend: function() {
                            $("input[name='salesInventories[0].inventory']").val('');
                            $("input[name='salesInventories[0].inventory.name']").val('');
                            $("textarea[name='salesInventories[0].inventory.description']").val('');
                        },
                        success: function(inventory) {
                            console.log(inventory);
                            $("input[name='salesInventories[0].inventory']").val(inventory.id);
                            $("input[name='salesInventories[0].inventory.name']").val(inventory.name);
                            $("textarea[name='salesInventories[0].inventory.description']").val(inventory.description);
                            swal.close();
                        },
                        error: function() {
                            swal.fire({
                                title: "Xəta baş verdi!",
                                html: "Əlaqə saxlamağınızı xahiş edirik.",
                                type: "error",
                                cancelButtonText: 'Bağla',
                                cancelButtonColor: '#c40000',
                                cancelButtonClass: 'btn btn-danger',
                                footer: '<a href>Məlumatlar yenilənsinmi?</a>'
                            });
                        }
                    })
                }
            });
        }
    }

    $('#modal-operation').on('shown.bs.modal', function() {
        $(document).off('focusin.modal');
    });

    var KTDatatablesBasicBasic = function() {

        var initTable1 = function() {
            var table = $('#schedule-table');

            table.DataTable({
                responsive: true,

                // DOM Layout settings
                dom: "<'row'<'col-sm-12'tr>><'row'<'col-sm-12 col-md-5'i><'col-sm-12 col-md-7 dataTables_pager'lp>>",

                lengthMenu: [5, 10, 25, 50, 75, 100, 200],

                pageLength: 25,

                language: {
                    'lengthMenu': 'Display _MENU_',
                },
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

            table.on('change', '.kt-group-checkable', function() {
                var set = $(this).closest('table').find('td:first-child .kt-checkable');
                var checked = $(this).is(':checked');

                $(set).each(function() {
                    if (checked) {
                        $(this).prop('checked', true);
                        $(this).closest('tr').addClass('active');
                    }
                    else {
                        $(this).prop('checked', false);
                        $(this).closest('tr').removeClass('active');
                    }
                });
            });

            table.on('change', 'tbody tr .kt-checkbox', function() {
                $(this).parents('tr').toggleClass('active');
            });
        };

        return {
            init: function() {
                initTable1();
            },
        };
    }();

    <c:if test="${edit.status}">
    $('#group_table tbody').on('dblclick', 'tr', function () {
        edit($('#form'), $(this).attr('data'), 'modal-operation', 'Redaktə');
    });
    </c:if>

    var KTFormRepeater = function() {
        var demo1 = function() {
            $('#kt_repeater_1').repeater({
                initEmpty: false,

                defaultValues: {
                    'text-input': 'foo'
                },

                show: function () {
                    var elements = $($(this).parent()).find(".align-items-center");
                    //alert(elements.length);
                    console.log(elements);
                    console.log(elements[0]);
                    $(this).slideDown();
                },

                hide: function (deleteElement) {
                    swal.fire({
                        title: 'İnventarı ləğv etməyə əminsinizmi?',
                        type: 'info',
                        allowEnterKey: true,
                        showCancelButton: true,
                        buttonsStyling: false,
                        cancelButtonText: 'Xeyr, edilməsin!',
                        cancelButtonClass: 'btn btn-danger',
                        confirmButtonText: 'Bəli, edilsin!',
                        confirmButtonClass: 'btn btn-success',
                        reverseButtons: true,
                        allowOutsideClick: false,
                        footer: '<a href>Məlumatlar yenilənsinmi?</a>'
                    }).then(function(result){
                        console.log(result);
                        if(result.value){
                            $(this).slideUp(deleteElement);
                        }
                    })
                }
            });
        };
        return {
            init: function() {
                demo1();
            }
        };
    }();

    jQuery(document).ready(function() {
        KTFormRepeater.init();
    });
</script>