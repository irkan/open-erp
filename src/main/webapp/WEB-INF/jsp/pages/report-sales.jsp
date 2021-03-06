<%--
  Created by IntelliJ IDEA.
  User: irkan.ahmadov
  Date: 20.09.2019
  Time: 1:22
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="utl" uri="/WEB-INF/tld/Util.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="random" class="java.util.Random" scope="application"/>
<div class="kt-container  kt-grid__item kt-grid__item--fluid">
    <c:set var="filter" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'filter')}"/>
    <c:if test="${filter.status}">
        <div class="accordion  accordion-toggle-arrow mb-2" id="accordionFilter">
            <div class="card" style="border-radius: 4px;">
                <div class="card-header">
                    <div class="card-title w-100" data-toggle="collapse" data-target="#filterContent" aria-expanded="true" aria-controls="collapseOne4">
                        <div class="row w-100">
                            <div class="col-3">
                                <i class="<c:out value="${filter.object.icon}"/>"></i>
                            </div>
                            <div class="col-6 text-center" style="letter-spacing: 10px;">
                                <c:out value="${filter.object.name}"/>
                            </div>
                        </div>
                    </div>
                </div>
                <div id="filterContent" class="collapse show" aria-labelledby="headingOne" data-parent="#accordionFilter">
                    <div class="card-body">
                        <form:form modelAttribute="filter" id="filter" method="post" action="/report/api/report-sales">
                            <div class="row">
                                <div class="col-md-10">
                                    <div class="row">
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="integer1">Baxış periodu</form:label>
                                                <form:select path="integer1" cssClass="custom-select form-control">
                                                    <form:option value="0">Son 1 həftəlik</form:option>
                                                    <form:option value="1">Son 1 aylıq</form:option>
                                                    <form:option value="2">Son 12 aylıq</form:option>
                                                    <form:option value="3">Son 10 illik</form:option>
                                                </form:select>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="string1">Struktur</form:label>
                                                <form:select path="string1" cssClass="custom-select form-control">
                                                     <form:option value=" ">Bütün fliallar üzrə</form:option>
                                                    <form:options items="${organizations}" itemLabel="name" itemValue="reportCondition"/>
                                                </form:select>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="string2">Satış növü</form:label>
                                                <form:select path="string2" cssClass="custom-select form-control">
                                                    <form:option value=" ">Bütün növlər üzrə</form:option>
                                                    <form:option value=" and s1.is_service=0 ">Satış</form:option>
                                                    <form:option value=" and s1.is_service=1 ">Servis</form:option>
                                                </form:select>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="string3">Müqavilə növü</form:label>
                                                <form:select path="string3" cssClass="custom-select form-control">
                                                    <form:option value=" ">Bütün müqavilələr üzrə</form:option>
                                                    <form:option value=" and p1.is_cash=0 ">Kredit</form:option>
                                                    <form:option value=" and p1.is_cash=1 ">Nağd</form:option>
                                                </form:select>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="string4">Müqavilə statusu</form:label>
                                                <form:select path="string4" cssClass="custom-select form-control">
                                                    <form:option value=" ">Bütün müqavilələr üzrə</form:option>
                                                    <form:option value=" and s1.is_saled=0 ">Davam edir</form:option>
                                                    <form:option value=" and s1.is_saled=1 ">Bitib</form:option>
                                                </form:select>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="string5">Müqavilə təsdiqi</form:label>
                                                <form:select path="string5" cssClass="custom-select form-control">
                                                    <form:option value=" and s1.is_returned=0 ">Aktual</form:option>
                                                    <form:option value=" and s1.is_returned=1 ">Qaytarılmış</form:option>
                                                </form:select>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="string6">Müqavilə statusu</form:label>
                                                <form:select path="string6" cssClass="custom-select form-control">
                                                    <form:option value=" and s1.is_active=1 ">Aktiv</form:option>
                                                    <form:option value=" and s1.is_active=0 ">Silinmiş</form:option>
                                                </form:select>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-2 text-right">
                                    <div class="form-group pt-3">
                                        <button class="btn btn-warning btn-elevate btn-icon-sm btn-block mt-2">
                                            <i class="la la-search"></i> Hesabla
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>
    </c:if>

    <div class="row">
        <div class="col-lg-12">
            <div class="kt-portlet kt-portlet--mobile">
                <div id="chart" class="pl-1 pr-3 pt-2"></div>
            </div>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/apexcharts"></script>
<script>
    $(function(){
        var options = {
            series: [{
                name: 'Aktiv müqavilələrin sayı',
                type: 'column',
                data: []
            }, {
                name: 'Təsdiq gözləyir',
                type: 'column',
                data: []
            }, {
                name: 'Yığım',
                type: 'line',
                data: []
            }],
            chart: {
                height: $( window ).height()-3*$("#accordionFilter").height()>350?$( window ).height()-2*$("#accordionFilter").height():350,
                type: 'line',
                stacked: false
            },
            dataLabels: {
                enabled: false
            },
            stroke: {
                width: [1, 1, 4]
            },
            title: {
                text: 'XYZ - 3 ölçülü satış analizi',
                align: 'left',
                offsetX: 50
            },
            xaxis: {
                categories: [],
            },
            yaxis: [
                {
                    axisTicks: {
                        show: true,
                    },
                    axisBorder: {
                        show: true,
                        color: '#008FFB'
                    },
                    labels: {
                        style: {
                            colors: '#008FFB',
                        }
                    },
                    title: {
                        text: "Aktiv müqavilələrin sayı",
                        style: {
                            color: '#008FFB',
                        }
                    },
                    tooltip: {
                        enabled: true
                    }
                },
                {
                    seriesName: 'Income',
                    opposite: true,
                    axisTicks: {
                        show: true,
                    },
                    axisBorder: {
                        show: true,
                        color: '#00E396'
                    },
                    labels: {
                        style: {
                            colors: '#00E396',
                        }
                    },
                    title: {
                        text: "Təsdiq gözləyən müqavilələrin sayı",
                        style: {
                            color: '#00E396',
                        }
                    },
                    tooltip: {
                        enabled: true
                    },
                },
                {
                    seriesName: 'Revenue',
                    opposite: true,
                    axisTicks: {
                        show: true,
                    },
                    axisBorder: {
                        show: true,
                        color: '#FEB019'
                    },
                    labels: {
                        style: {
                            colors: '#FEB019',
                        },
                    },
                    title: {
                        text: "Yığım həcmi (AZN - manatlla ifadədə)",
                        style: {
                            color: '#FEB019',
                        }
                    },
                    tooltip: {
                        enabled: true
                    }
                },
            ],
            tooltip: {
                fixed: {
                    enabled: true,
                    position: 'topLeft', // topRight, topLeft, bottomRight, bottomLeft
                    offsetY: 30,
                    offsetX: 60
                },
            },
            legend: {
                horizontalAlign: 'left',
                offsetX: 20
            }
        };

        var chart = new ApexCharts(document.querySelector("#chart"), options);
        chart.render();

        $("#filter").ajaxForm({
            beforeSubmit: function(){
                swal.showLoading();

                options.xaxis.categories = [];
                options.series[0].data = [];
                options.series[1].data = [];
                options.series[2].data = [];
            },
            success: function(data){

                $.each(jQuery.parseJSON(data.string1), function(key, val){
                    options.xaxis.categories.push(val.ZAXIS);
                    options.series[0].data.push(round(val.YAXIS, 0));
                });

                $.each(jQuery.parseJSON(data.string2), function(key, val){
                    options.series[1].data.push(round(val.YAXIS, 0));
                });

                $.each(jQuery.parseJSON(data.string3), function(key, val){
                    options.series[2].data.push(round(val.YAXIS, 0));
                });
                chart.updateOptions(options);

                swal.close();
            },
            complete: function(){
                swal.close();
            },
            error: function(){
                swal.close();
            }
        });
    });
</script>