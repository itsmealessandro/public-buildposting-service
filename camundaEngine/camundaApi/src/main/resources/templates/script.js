function addCityField() {
  const container = document.getElementById("cities-container");
  const div = document.createElement("div");
  div.innerHTML = `
    <select class="city" required>
      <option value="">Select City</option>
      <option value="L'Aquila">L'Aquila</option>
      <option value="Rome">Rome</option>
      <option value="Milan">Milan</option>
    </select>
    <input type="number" step="0.01" class="maxPrice" placeholder="Max Price" required>
    <button type="button" onclick="this.parentElement.remove()">Remove</button>
  `;
  container.appendChild(div);
}

async function submitBooking(event) {
  event.preventDefault();

  const username = document.getElementById("username").value;
  const format = document.getElementById("format").value;
  const algorithm = document.getElementById("algorithm").value;

  const cities = Array.from(document.getElementsByClassName("city")).map(input => input.value);
  const maxPricesArr = Array.from(document.getElementsByClassName("maxPrice")).map(input => parseFloat(input.value));

  const maxPrices = {};
  cities.forEach((city, index) => {
    maxPrices[city] = maxPricesArr[index];
  });

  const requestBody = {
    username: username,
    cities: cities,
    format: format,
    algorithm: algorithm,
    maxPrices: maxPrices
  };

  try {
    const response = await fetch('/api/booking/request', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(requestBody)
    });

    if (response.ok) {
      alert("Booking request sent successfully!");
    } else {
      const text = await response.text();
      alert("Error: " + response.status + "\n" + text);
    }
  } catch (err) {
    alert("Request failed: " + err);
  }
}
