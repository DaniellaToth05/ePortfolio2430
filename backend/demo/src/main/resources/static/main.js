
function showMessage(id, message, isSuccess = true) {
const msgEl = document.getElementById(id);
msgEl.textContent = message;
msgEl.className = "confirmation-message " + (isSuccess ? "success" : "error");
msgEl.style.display = "block";
setTimeout(() => {
    msgEl.style.opacity = "0";
    setTimeout(() => {
    msgEl.style.display = "none";
    msgEl.style.opacity = "1";
    msgEl.className = "confirmation-message";
    msgEl.textContent = "";
    }, 300);
}, 2500);
}

// Buy modal logic
function openBuyModal() {
document.getElementById('buyModalOverlay').style.display = 'flex';
document.body.style.overflow = 'hidden';
setTimeout(() => document.getElementById('buy-symbol').focus(), 300);
}
function closeBuyModal() {
document.getElementById('buyModalOverlay').style.display = 'none';
document.body.style.overflow = 'auto';
}

// Sell modal logic
function openSellModal() {
document.getElementById('sellModalOverlay').style.display = 'flex';
document.body.style.overflow = 'hidden';
setTimeout(() => document.getElementById('sell-symbol').focus(), 300);
}
function closeSellModal() {
document.getElementById('sellModalOverlay').style.display = 'none';
document.body.style.overflow = 'auto';
}

// Modal open buttons
const addBtn = Array.from(document.querySelectorAll(".action-btn")).find(btn => btn.textContent.includes("Add Investment"));
if (addBtn) addBtn.onclick = openBuyModal;

const sellBtn = Array.from(document.querySelectorAll(".action-btn")).find(btn => btn.textContent.includes("Sell Investment"));
if (sellBtn) sellBtn.onclick = openSellModal;

// Buy form submit
document.getElementById("buyForm").addEventListener("submit", async function (e) {
e.preventDefault();
const data = {
    symbol: document.getElementById("buy-symbol").value,
    name: document.getElementById("buy-name").value,
    quantity: document.getElementById("buy-quantity").value,
    price: document.getElementById("buy-price").value
};
try {
    const res = await fetch("/api/buyStock", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(data)
    });
    if (res.ok) {
    showMessage("buyConfirmation", `Successfully bought ${data.quantity} shares of ${data.symbol}.`);
    this.reset();
    } else {
    showMessage("buyConfirmation", "Purchase failed. Try again.", false);
    }
} catch (err) {
    showMessage("buyConfirmation", "An error occurred. Try again.", false);
}
});

// Sell form submit
document.getElementById("sellForm").addEventListener("submit", async function (e) {
e.preventDefault();
const data = {
    symbol: document.getElementById("sell-symbol").value,
    quantity: document.getElementById("sell-quantity").value,
    price: document.getElementById("sell-price").value
};
try {
    const res = await fetch("/api/sellStock", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(data)
    });
    if (res.ok) {
    showMessage("sellConfirmation", `Successfully sold ${data.quantity} shares of ${data.symbol}.`);
    this.reset();
    } else {
    showMessage("sellConfirmation", "Sale failed. Try again.", false);
    }
} catch (err) {
    showMessage("sellConfirmation", "An error occurred. Try again.", false);
}
});

// Search modal logic
function openSearchModal() {
document.getElementById('searchModalOverlay').style.display = 'flex';
document.body.style.overflow = 'hidden';
setTimeout(() => document.getElementById('search-symbol').focus(), 300);
}
function closeSearchModal() {
document.getElementById('searchModalOverlay').style.display = 'none';
document.body.style.overflow = 'auto';
}

// Open button
const searchBtn = Array.from(document.querySelectorAll(".action-btn")).find(btn => btn.textContent.includes("Portfolio Search"));
if (searchBtn) searchBtn.onclick = openSearchModal;

// Search form submit
document.getElementById("searchForm").addEventListener("submit", async function (e) {
e.preventDefault();
const symbol = document.getElementById("search-symbol").value;
const resultEl = document.getElementById("searchResult");

try {
    const res = await fetch(`/api/lookupStock?symbol=${encodeURIComponent(symbol)}`);
    if (res.ok) {
    const data = await res.json();
    resultEl.className = "confirmation-message success";
    resultEl.textContent = `${data.symbol} – ${data.name}: ${data.quantity} shares @ $${data.price}`;
    } else if (res.status === 404) {
    resultEl.className = "confirmation-message error";
    resultEl.textContent = `No holdings found for "${symbol}".`;
    } else {
    throw new Error("Lookup failed");
    }
} catch (err) {
    resultEl.className = "confirmation-message error";
    resultEl.textContent = "Error searching. Please try again.";
}

resultEl.style.display = "block";
setTimeout(() => {
    resultEl.style.opacity = "0";
    setTimeout(() => {
    resultEl.style.display = "none";
    resultEl.style.opacity = "1";
    resultEl.textContent = "";
    }, 300);
}, 3000);
});

// Update modal logic
function openUpdateModal() {
document.getElementById('updateModalOverlay').style.display = 'flex';
document.body.style.overflow = 'hidden';
setTimeout(() => document.getElementById('update-symbol').focus(), 300);
}
function closeUpdateModal() {
document.getElementById('updateModalOverlay').style.display = 'none';
document.body.style.overflow = 'auto';
}

// Button handler
const updateBtn = Array.from(document.querySelectorAll(".action-btn")).find(btn => btn.textContent.includes("Update Prices"));
if (updateBtn) updateBtn.onclick = openUpdateModal;

// Update form submit
document.getElementById("updateForm").addEventListener("submit", async function (e) {
e.preventDefault();
const data = {
    symbol: document.getElementById("update-symbol").value,
    price: document.getElementById("update-price").value
};
try {
    const res = await fetch("/api/updatePrice", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(data)
    });
    if (res.ok) {
    showMessage("updateConfirmation", `Updated ${data.symbol} to $${data.price} per share.`);
    this.reset();
    } else {
    showMessage("updateConfirmation", "Update failed. Try again.", false);
    }
} catch (err) {
    showMessage("updateConfirmation", "An error occurred. Try again.", false);
}
});

// Goal section animation on scroll
const observerOptions = {
threshold: 0.2,
rootMargin: '0px 0px -30px 0px'
};

const observer = new IntersectionObserver((entries) => {
entries.forEach(entry => {
    if (entry.isIntersecting) {
    const progressBars = entry.target.querySelectorAll('.progress-fill');
    progressBars.forEach((bar, index) => {
        const originalWidth = bar.style.width;
        bar.style.transition = 'none';
        bar.style.width = '0%';
        setTimeout(() => {
        bar.style.transition = 'width 2s ease';
        bar.style.width = originalWidth;
        }, 150 + (index * 250));
    });
    }
});
}, observerOptions);

const goalsSection = document.querySelector('.goals-section');
if (goalsSection) observer.observe(goalsSection);

