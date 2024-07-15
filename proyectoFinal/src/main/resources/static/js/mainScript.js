// Function to load landing page after successful logout
function loadLandingPage() {
    $.ajax({
        url: "/",
        type: 'GET',
        success: function() {
            localStorage.removeItem('jwtToken');
            localStorage.removeItem('username');
            window.location.href = "/";
        },
        error: function(xhr, status, error) {
            console.error('Error fetching Home page data:', error);
            Swal.fire('Error', 'Failed to load home page data. Please try again.', 'error');
        }
    });
}