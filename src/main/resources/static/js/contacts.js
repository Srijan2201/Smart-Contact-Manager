console.log("contacts js file is loaded");
const baseURL = "http://localhost:8081";
const viewContactModal = document.getElementById("view_contact_modal");

// options with default values
const options = {
    placement: 'bottom-right',
    backdrop: 'dynamic',
    backdropClasses:
        'bg-gray-900/50 dark:bg-gray-900/80 fixed inset-0 z-40',
    closable: true,
    onHide: () => {
        console.log('modal is hidden');
    },
    onShow: () => {
        console.log('modal is shown');
    },
    onToggle: () => {
        console.log('modal has been toggled');
    },
};

// instance options object
const instanceOptions = {
  id: 'view_contact_modal',
  override: true
};

const contactModal = new Modal(viewContactModal, options, instanceOptions);

function openContactModal() {
contactModal.show();
}

function closeContactModal() {
contactModal.hide();
}

async function loadContactdata(id) {
    console.log(id);

    try {
        const data = await (await fetch(`${baseURL}/api/contacts/${id}`)).json();
        console.log(data); //

        document.querySelector("#contact_name").innerHTML = data.name;
        document.querySelector("#contact_email").innerHTML = data.email;
        document.querySelector("#contact_image").src = data.picture;
        document.querySelector("#contact_address").innerHTML = data.address;
        document.querySelector("#contact_phone").innerHTML = data.phoneNumber;
        document.querySelector("#contact_about").innerHTML = data.description;

        const contactFavorite = document.querySelector("#contact_favorite");
        if (data.favourite) {
            contactFavorite.innerHTML = "<i class='fa-solid fa-star text-yellow-400'></i>";
        } else {
            contactFavorite.innerHTML = "Not Favourite Contact";
        }

        const websiteEl = document.querySelector("#contact_website");
        const websiteUrl = data.websiteLink || data.website;
        websiteEl.href = websiteUrl || "#";
        websiteEl.innerHTML = websiteUrl || "N/A";

        const linkedinEl = document.querySelector("#contact_linkedin");
        const linkedinUrl = data.linkedInLink || data.linkedinLink; // handles both casings
        linkedinEl.href = linkedinUrl || "#";
        linkedinEl.innerHTML = linkedinUrl || "N/A";

        openContactModal();

    } catch (error) {
        console.log("Error", error);
    }
    
}

async function deleteContact(id) {
    Swal.fire({
  title: "Do you want to delete this contact?",
  icon: "warning",
  showCancelButton: true,
  confirmButtonText: "Delete",
}).then((result) => {
  /* Read more about isConfirmed, isDenied below */
  if (result.isConfirmed){
    const url = `${baseURL}/user/contacts/delete/`+id;
    // Swal.fire("Deleted!", "", "success");
    window.location.replace(url);
  } 
  
});
}