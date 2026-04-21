// Main Application Logic for Lumina Store

const products = [
    { id: 101, name: 'Lumina Echo Buds', category: 'Audio', price: 129.00, img: '🎧', badge: 'New', desc: 'Premium noise-canceling wireless earbuds with 24h battery.' },
    { id: 102, name: 'Nexus Smartwatch Pro', category: 'Wearables', price: 299.00, img: '⌚', badge: '', desc: 'Advanced health tracking and always-on display.' },
    { id: 103, name: 'Aura Mechanical Keyboard', category: 'Accessories', price: 149.00, img: '⌨️', badge: 'Sale', desc: 'Tactile mechanical switches with subtle white backlighting.' },
    { id: 104, name: 'Vortex Wireless Mouse', category: 'Accessories', price: 79.00, img: '🖱️', badge: '', desc: 'Ergonomic precision mouse for productivity.' },
    { id: 105, name: 'Titan Fast Charger 65W', category: 'Accessories', price: 49.00, img: '🔌', badge: '', desc: 'Compact GaN charger with dual USB-C ports.' },
    { id: 106, name: 'Zenith Studio Monitor', category: 'Displays', price: 499.00, img: '🖥️', badge: '', desc: '4K color-accurate display for creators.' }
];

let cart = JSON.parse(localStorage.getItem('lumina_cart')) || [];

function saveCart() {
    localStorage.setItem('lumina_cart', JSON.stringify(cart));
    updateCartCount();
}

function updateCartCount() {
    const badge = document.getElementById('cart-badge');
    if (badge) {
        const count = cart.reduce((sum, item) => sum + item.quantity, 0);
        badge.textContent = count;
        badge.style.display = count > 0 ? 'flex' : 'none';
    }
}

function showToast(message, type = 'success') {
    let container = document.getElementById('toast-container');
    if (!container) {
        container = document.createElement('div');
        container.id = 'toast-container';
        container.className = 'toast-container';
        document.body.appendChild(container);
    }

    const toast = document.createElement('div');
    toast.className = `toast ${type}`;
    toast.innerHTML = `
        <span>${type === 'success' ? '✓' : '⚠'}</span>
        <span>${message}</span>
    `;

    container.appendChild(toast);
    setTimeout(() => {
        toast.style.animation = 'fadeOut 0.3s forwards';
        setTimeout(() => toast.remove(), 300);
    }, 3000);
}

function addToCart(productId) {
    const product = products.find(p => p.id === productId);
    if (!product) return;

    const existing = cart.find(item => item.id === productId);
    if (existing) {
        existing.quantity++;
    } else {
        cart.push({ ...product, quantity: 1 });
    }
    saveCart();
    showToast(`${product.name} added to cart!`);
}

function renderProducts(elementId, limit = null, categoryFilter = null) {
    const grid = document.getElementById(elementId);
    if (!grid) return;

    grid.innerHTML = '';
    let filtered = products;

    if (categoryFilter) {
        filtered = filtered.filter(p => p.category.toLowerCase() === categoryFilter.toLowerCase());
    }

    // Apply basic text filter from URL if on shop page
    const urlParams = new URLSearchParams(window.location.search);
    const searchParam = urlParams.get('search');
    if (searchParam) {
        filtered = filtered.filter(p => p.name.toLowerCase().includes(searchParam.toLowerCase()));
        const searchInput = document.getElementById('search-input');
        if (searchInput) searchInput.value = searchParam;
    }

    const sortParam = urlParams.get('sort');
    if (sortParam) {
        if (sortParam === 'price_asc') filtered.sort((a,b) => a.price - b.price);
        if (sortParam === 'price_desc') filtered.sort((a,b) => b.price - a.price);
    }

    const itemsToRender = limit ? filtered.slice(0, limit) : filtered;

    if (itemsToRender.length === 0) {
        grid.innerHTML = `<div class="empty-state" style="grid-column: 1 / -1;"><p>No products found matching your criteria.</p></div>`;
        return;
    }

    itemsToRender.forEach(p => {
        const badgeHtml = p.badge ? `<span class="badge ${p.badge === 'Sale' ? 'badge-sale' : 'badge-new'}" style="position:absolute; top:1rem; right:1rem;">${p.badge}</span>` : '';
        
        const card = document.createElement('div');
        card.className = 'product-card hover-trigger';
        card.dataset.id = p.id;
        card.draggable = true;
        card.addEventListener('dragstart', (e) => {
            e.dataTransfer.setData('text/plain', p.name);
        });
        card.innerHTML = `
            <div class="product-image-wrap" style="position:relative; font-size:4rem;">
                ${badgeHtml}
                ${p.img}
            </div>
            <div class="product-info">
                <div class="product-category">${p.category}</div>
                <h3 class="product-title">${p.name}</h3>
                <div class="product-price">$${p.price.toFixed(2)}</div>
                <button class="btn btn-primary btn-block add-to-cart-btn" data-id="${p.id}">Add to Cart</button>
            </div>
        `;
        grid.appendChild(card);
    });

    document.querySelectorAll('.add-to-cart-btn').forEach(btn => {
        btn.addEventListener('click', (e) => {
            const id = parseInt(e.target.dataset.id);
            addToCart(id);
        });
    });
}

function renderCart() {
    const tableBody = document.getElementById('cart-tbody');
    const summarySubtotal = document.getElementById('summary-subtotal');
    const summaryTotal = document.getElementById('summary-total');

    if (!tableBody) return;

    if (cart.length === 0) {
        tableBody.innerHTML = `<tr><td colspan="4" class="empty-state">Your cart is empty. <a href="shop.html" style="color:var(--color-primary);text-decoration:underline;">Go shopping</a></td></tr>`;
        if (summarySubtotal) summarySubtotal.textContent = '$0.00';
        if (summaryTotal) summaryTotal.textContent = '$0.00';
        document.getElementById('checkout-btn')?.setAttribute('disabled', 'true');
        return;
    }

    tableBody.innerHTML = '';
    let subtotal = 0;

    cart.forEach(item => {
        const itemTotal = item.price * item.quantity;
        subtotal += itemTotal;

        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td>
                <div class="cart-product">
                    <div class="cart-img" style="font-size:2rem;">${item.img}</div>
                    <div>
                        <div style="font-weight:600;">${item.name}</div>
                        <div style="font-size:0.875rem; color:var(--color-text-muted);">$${item.price.toFixed(2)}</div>
                    </div>
                </div>
            </td>
            <td>
                <div class="qty-controls">
                    <button class="qty-btn minus-btn" data-id="${item.id}">-</button>
                    <span style="min-width:20px;text-align:center;">${item.quantity}</span>
                    <button class="qty-btn plus-btn" data-id="${item.id}">+</button>
                </div>
            </td>
            <td style="font-weight:600;">$${itemTotal.toFixed(2)}</td>
            <td>
                <button class="remove-btn" data-id="${item.id}">Remove</button>
            </td>
        `;
        tableBody.appendChild(tr);
    });

    const tax = subtotal * 0.05; // 5% tax
    const total = subtotal + tax;

    if (summarySubtotal) summarySubtotal.textContent = `$${subtotal.toFixed(2)}`;
    const taxEl = document.getElementById('summary-tax');
    if (taxEl) taxEl.textContent = `$${tax.toFixed(2)}`;
    if (summaryTotal) summaryTotal.textContent = `$${total.toFixed(2)}`;
    
    document.getElementById('checkout-btn')?.removeAttribute('disabled');

    // Attach events
    document.querySelectorAll('.plus-btn').forEach(btn => {
        btn.addEventListener('click', (e) => {
            const id = parseInt(e.target.dataset.id);
            const item = cart.find(c => c.id === id);
            if(item) { item.quantity++; saveCart(); renderCart(); }
        });
    });

    document.querySelectorAll('.minus-btn').forEach(btn => {
        btn.addEventListener('click', (e) => {
            const id = parseInt(e.target.dataset.id);
            const item = cart.find(c => c.id === id);
            if(item) { 
                item.quantity--; 
                if(item.quantity <= 0) cart = cart.filter(c => c.id !== id);
                saveCart(); renderCart(); 
            }
        });
    });

    document.querySelectorAll('.remove-btn').forEach(btn => {
        btn.addEventListener('click', (e) => {
            const id = parseInt(e.target.dataset.id);
            cart = cart.filter(c => c.id !== id);
            saveCart(); renderCart();
            showToast('Item removed from cart', 'error');
        });
    });
}

// Check auth state
function checkAuth() {
    const user = localStorage.getItem('lumina_user');
    const authLink = document.getElementById('auth-link');
    if (authLink) {
        if (user) {
            authLink.innerHTML = `<span style="display:flex;align-items:center;gap:4px;">👤 ${user} <button id="logout-btn" style="color:var(--color-text-muted);font-size:0.8rem;margin-left:8px;">[Logout]</button></span>`;
            document.getElementById('logout-btn').addEventListener('click', (e) => {
                e.preventDefault();
                localStorage.removeItem('lumina_user');
                window.location.reload();
            });
        }
    }
}

// Initial setup
document.addEventListener('DOMContentLoaded', () => {
    updateCartCount();
    checkAuth();

    // Pages initialization
    if (document.getElementById('featured-grid')) {
        renderProducts('featured-grid', 4);
    }
    
    if (document.getElementById('shop-grid')) {
        renderProducts('shop-grid');
        
        // Setup shop filters via URL params handling
        const filterForm = document.getElementById('filter-form');
        if (filterForm) {
            filterForm.addEventListener('submit', (e) => {
                e.preventDefault();
                const q = document.getElementById('search-input').value;
                window.location.href = `shop.html?search=${encodeURIComponent(q)}`;
            });
        }
    }

    if (document.getElementById('cart-tbody')) {
        renderCart();
    }

    // Login page handling
    const loginForm = document.getElementById('login-form');
    if (loginForm) {
        loginForm.addEventListener('submit', (e) => {
            e.preventDefault();
            const email = document.getElementById('email').value;
            const password = document.getElementById('password').value;
            const errorEl = document.getElementById('login-error');
            errorEl.style.display = 'none';

            // Very simple demo validation
            if (password.length < 6) {
                errorEl.textContent = 'Password must be at least 6 characters.';
                errorEl.style.display = 'block';
                return;
            }

            if (email.includes('@') && password === 'lumina2026') {
                localStorage.setItem('lumina_user', email.split('@')[0]);
                window.location.href = 'index.html';
            } else {
                errorEl.textContent = 'Invalid email or password. Hint: lumina2026';
                errorEl.style.display = 'block';
            }
        });
    }

    // Checkout page handling
    const checkoutForm = document.getElementById('checkout-form');
    if (checkoutForm) {
        // Pre-fill summary
        document.getElementById('checkout-total').textContent = '$' + ((cart.reduce((s,i)=>s+i.price*i.quantity,0))*1.05).toFixed(2);
        
        checkoutForm.addEventListener('submit', (e) => {
            e.preventDefault();
            
            // Check terms
            if (!document.getElementById('terms').checked) {
                showToast('Please accept the terms to proceed.', 'error');
                return;
            }

            // Simulate processing
            const btn = document.getElementById('place-order-btn');
            const origText = btn.textContent;
            btn.textContent = 'Processing...';
            btn.disabled = true;

            setTimeout(() => {
                cart = [];
                saveCart();
                document.getElementById('checkout-container').innerHTML = `
                    <div class="empty-state">
                        <div style="font-size:4rem;margin-bottom:1rem;">✅</div>
                        <h2>Order Confirmed!</h2>
                        <p style="margin-top:0.5rem;color:var(--color-text-muted);">Thank you for shopping at Lumina. Your order #LUM-${Math.floor(10000+Math.random()*90000)} has been placed.</p>
                        <a href="shop.html" class="btn btn-primary" style="margin-top:2rem;">Continue Shopping</a>
                    </div>
                `;
            }, 1500);
        });
    }
});
