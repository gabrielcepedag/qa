$(document).ready(function() {
    var tableProducts = $('#tableProducts').DataTable({
        layout: {
            topStart: {
                buttons: [
                    {
                        extend: 'excel',
                        text: '<i class="fas fa-file-excel"></i>',
                        className: 'btn-sm btn-success px-4'
                    },
                    {
                        extend: 'pdf',
                        text: '<i class="fas fa-file-pdf"></i>',
                        className: 'btn-sm btn-danger px-4'
                    },
                    {
                        extend: 'print',
                        text: '<i class="fas fa-print"></i>',
                        className: 'btn-sm btn-secondary px-4'
                    }
                ]
            }
        },
        pageLength: 10,
        lengthMenu: [5, 10, 15, 25, 50],
        language: {
            search: "Search:",
            lengthMenu: "Show _MENU_ products",
            info: "Showing _END_ from _TOTAL_ products",
            infoEmpty: "No products registered",
            infoFiltered: "(filtered from _MAX_ total products)",
            zeroRecords: "No matching products found",
            emptyTable: "No products registered",
            responsive: true,
            paginate: {
                first: "&#8676;",
                previous: "&#8592;",
                next: "&#8594;",
                last: "&#8677;"
            }
        },
    });
    $('#tableProducts_wrapper .row .col-12 .table .header tr th').addClass('dark');
});