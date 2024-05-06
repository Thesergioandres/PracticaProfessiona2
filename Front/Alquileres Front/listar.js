document.addEventListener("DOMContentLoaded", function () {
  // Obtener la lista de propiedades del backend y mostrarlas en la tabla
  function fetchProperties() {
    fetch("http://localhost:8080/api/properties/all")
      .then((response) => response.json())
      .then((data) => {
        const propertyList = document.getElementById("property-list");
        propertyList.innerHTML = ""; // Limpiar la lista antes de agregar nuevas filas
        data.forEach((property) => {
          const row = document.createElement("tr");
          row.innerHTML = `
                            <td>${property.name}</td>
                            <td>${property.location}</td>
                            <td><img src="${property.imageUrl}" alt="Imagen de la propiedad" style="max-width: 100px;"></td>
                            <td>${property.price}</td>
                            <td>
                                <button class="enable-button" data-id="${property.id}">Habilitar</button>
                                <button class="delete-button" data-id="${property.id}">Desabilitar</button>
                            </td>
                        `;
          propertyList.appendChild(row);
        });
      })
      .catch((error) => {
        console.error("Error al obtener la lista de propiedades:", error);
      });
  }

  // Escuchar clics en los botones de deshabilitar
  document
    .getElementById("property-list")
    .addEventListener("click", function (event) {
      if (event.target.classList.contains("delete-button")) {
        const propertyId = event.target.getAttribute("data-id");
        if (
          confirm("¿Estás seguro de que deseas deshabilitar esta propiedad?")
        ) {
          fetch(`http://localhost:8080/api/properties/delete/${propertyId}`, {
            method: "PUT",
          })
            .then((response) => {
              if (response.ok) {
                // Si la deshabilitacion fue exitosa, actualizar la lista de propiedades
                fetchProperties();
              } else {
                alert(
                  "Error al deshabilitar la propiedad. Por favor, inténtalo de nuevo."
                );
              }
            })
            .catch((error) => {
              console.error("Error al deshabilitar la propiedad:", error);
              alert(
                "Error al conectar con el servidor. Por favor, inténtalo de nuevo más tarde."
              );
            });
        }
      }
    });

  document
    .getElementById("property-list")
    .addEventListener("click", function (event) {
      if (event.target.classList.contains("enable-button")) {
        const propertyId = event.target.getAttribute("data-id");
        if (confirm("¿Estás seguro de que deseas habilitar esta propiedad?")) {
          fetch(`http://localhost:8080/api/properties/enable/${propertyId}`, {
            method: "PUT",
          })
            .then((response) => {
              if (response.ok) {
                // Si la habilitacion fue exitosa, actualizar la lista de propiedades
                fetchProperties();
              } else {
                alert(
                  "Error al habilitar la propiedad. Por favor, inténtalo de nuevo."
                );
              }
            })
            .catch((error) => {
              console.error("Error al habilitar la propiedad:", error);
              alert(
                "Error al conectar con el servidor. Por favor, inténtalo de nuevo más tarde."
              );
            });
        }
      }
    });

  // Obtener la lista de propiedades cuando la página se carga inicialmente
  fetchProperties();
});
