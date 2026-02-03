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
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(requestBody)
    });

    if (!response.ok) {
      const text = await response.text();
      alert("Error: " + response.status + "\n" + text);
      return;
    }

    const data = await response.json();

    // Mostra il blocco con le informazioni ricevute
    document.getElementById("selected-zones").textContent = Array.isArray(data.selectedZones)
      ? data.selectedZones.join(", ")
      : data.selectedZones;

    document.getElementById("total-price").textContent = data.totalPrice;
    document.getElementById("booking-response").classList.remove("hidden");

    //  Salva dati necessari per la decisione
    localStorage.setItem("businessKey", data.businessKey);
    localStorage.setItem("requestId", data.requestId);

  } catch (err) {
    alert("Request failed: " + err);
  }
}

//  funzione comune per inviare la decisione
async function sendDecision(decision) {
  const businessKey = localStorage.getItem("businessKey");
  const requestId = localStorage.getItem("requestId");

  if (!businessKey || !requestId) {
    alert("Missing booking information.");
    return;
  }

  const decisionRequest = {
    requestId: requestId,
    decision: decision,
    businessKey: businessKey
  };

  try {
    const response = await fetch('/api/booking/decision', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(decisionRequest)
    });

    if (response.ok) {
      const result = await response.json();

      let msg = `Decision '${decision}' processed.`;
      if (result.invoiceNumber) {
        msg += `\nInvoice: ${result.invoiceNumber}`;
      }
      if (result.amountDue !== undefined) {
        msg += `\nAmount Due: €${result.amountDue}`;
      }

      alert(msg);
      document.getElementById("booking-response").classList.add("hidden");

      // Refresh history
      loadHistory();
    } else {
      const text = await response.text();
      alert("Error sending decision: " + response.status + "\n" + text);
    }
  } catch (err) {
    alert("Failed to send decision: " + err);
  }
}

// ✅ Gestione pulsanti
function confirmBooking() {
  sendDecision("CONFIRM");
}

function cancelBooking() {
  sendDecision("CANCEL");
}

async function loadHistory() {
  try {
    const response = await fetch('/api/booking/history');
    if (!response.ok) {
      console.error("Failed to load history");
      return;
    }
    const history = await response.json();
    console.log("Loaded history items:", history.length);
    const tbody = document.querySelector("#history-table tbody");
    tbody.innerHTML = "";

    if (history.length === 0) {
      tbody.innerHTML = "<tr><td colspan='7'>No history found.</td></tr>";
      return;
    }

    history.forEach(item => {
      const tr = document.createElement("tr");

      const date = item.startTime ? new Date(item.startTime).toLocaleString() : "";
      const username = item.username || "";
      const requestId = item.requestId || "";
      const decision = item.decision || "";
      const invoice = item.invoiceNumber || "-";
      const amount = item.amountDue ? "€" + item.amountDue : "-";

      // Determine if cancelled
      const isCancelled = decision === "CANCEL" || item.status === "CANCEL";
      if (isCancelled) {
        tr.classList.add("cancelled-row");
      }

      // Action button logic
      let actionHtml = "";
      if (!item.endTime && !decision) {
        // Pending request
        actionHtml = "Pending";
      } else if (isCancelled) {
        actionHtml = "Cancelled";
      } else {
        actionHtml = "Completed";
      }

      tr.innerHTML = `
                <td>${date}</td>
                <td>${username}</td>
                <td>${requestId}</td>
                <td>${decision}</td>
                <td>${invoice}</td>
                <td>${amount}</td>
                <td>${actionHtml}</td>
            `;
      tbody.appendChild(tr);
    });
  } catch (e) {
    console.error("Error loading history:", e);
  }
}

async function cancelRequest(requestId, businessKey) {
  if (!confirm("Are you sure you want to cancel this request?")) {
    return;
  }

  const decisionRequest = {
    requestId: requestId,
    decision: "CANCEL",
    businessKey: businessKey
  };

  try {
    const response = await fetch('/api/booking/decision', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(decisionRequest)
    });

    if (response.ok) {
      alert("Request cancelled successfully.");
      loadHistory();
    } else {
      const text = await response.text();
      alert("Error cancelling request: " + response.status + "\n" + text);
    }
  } catch (err) {
    alert("Failed to cancel request: " + err);
  }
}

// Load history on page load
document.addEventListener("DOMContentLoaded", loadHistory);
