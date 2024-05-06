document.getElementById("imageUrl").addEventListener("change", function () {
  var imageUrl = document.getElementById("imageUrl").value;
  var imagePreview = document.getElementById("image-preview");
  imagePreview.src = imageUrl;
});

document
  .getElementById("property-form")
  .addEventListener("submit", function (event) {
    event.preventDefault(); // Evitar que el formulario se envíe de forma predeterminada

    // Obtener la fecha y hora actual en formato ISO 8601 (YYYY-MM-DDTHH:mm:ss)
    var currentDate = new Date().toISOString();

    // Establecer el valor del campo createdAt en el formulario
    document.getElementById("createdAt").value = currentDate;
  });

document
  .getElementById("property-form")
  .addEventListener("submit", function (event) {
    event.preventDefault(); // Evitar que el formulario se envíe de forma predeterminada

    // Obtener los valores de los campos del formulario
    var name = document.getElementById("name").value;
    var location = document.getElementById("location").value;
    var imageUrl = document.getElementById("imageUrl").value;
    var price = parseFloat(document.getElementById("price").value);
    var createdAt = document.getElementById("createdAt").value;

    // Crear un objeto con los datos de la propiedad
    var propertyData = {
      name: name,
      location: location,
      imageUrl: imageUrl,
      price: price,
      createdAt: createdAt,
    };

    // Enviar los datos al backend
    fetch("http://localhost:8080/api/properties/create", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(propertyData),
    })
      .then((response) => {
        if (response.ok) {
          // Si la respuesta es exitosa, redirigir al usuario a otra página o realizar alguna acción adicional
          window.location.href = "/listar.html";
        } else {
          // Si hay un error en la respuesta, mostrar un mensaje de error al usuario
          alert(
            "Error al guardar la propiedad. Por favor, inténtalo de nuevo."
          );
        }
      })
      .catch((error) => {
        console.error("Error al enviar los datos al backend:", error);
        alert(
          "Error al conectar con el servidor. Por favor, inténtalo de nuevo más tarde."
        );
      });
  });
