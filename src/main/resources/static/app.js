const API_BASE_URL = 'http://localhost:8080/api/kobito';

// エラーメッセージ表示
function displayError(message) {
    document.getElementById('error-message').textContent = 'エラー: ' + message;
}

// ----------------------------------------------------
// 一覧表示用の共通関数（検索結果にも使う）
// ----------------------------------------------------
function displayKobitos(kobitos) {
    const listDiv = document.getElementById('kobito-list');
    listDiv.innerHTML = '';

    if (kobitos.length === 0) {
        listDiv.innerHTML = '<p>こびとはまだ見つかっていません。</p>';
        return;
    }

    kobitos.forEach(k => {
        const item = document.createElement('div');
        item.className = 'kobito-item';
        item.innerHTML = `
            <strong>ID: ${k.id}</strong><br>
            <strong>名前:</strong> ${k.name}<br>
            <strong>特徴:</strong> ${k.description?.substring(0, 50) || ''}...<br>
            ${k.imageUrl ? `<img src="${k.imageUrl}" width="120">` : ''}

            <br>
            <button onclick="goEditPage(${k.id})">編集する</button>
            <button onclick="deleteKobito(${k.id})">削除する</button>
        `;
        listDiv.appendChild(item);
    });
}

// ----------------------------------------------------
// GET: 全件取得
// ----------------------------------------------------
async function fetchKobitos() {
    document.getElementById('error-message').textContent = '';

    try {
        const response = await fetch(API_BASE_URL, { cache: "no-store" });
        if (!response.ok) throw new Error(`HTTPエラー: ${response.status}`);

        const kobitos = await response.json();
        displayKobitos(kobitos);

    } catch (error) {
        displayError(`こびと一覧の取得に失敗しました: ${error.message}`);
    }
}

// ----------------------------------------------------
// POST: 新規登録
// ----------------------------------------------------
document.getElementById('create-form').addEventListener('submit', async (event) => {
    event.preventDefault();
    document.getElementById('error-message').textContent = '';

    const name = document.getElementById('name').value;
    const description = document.getElementById('description').value;
    const imageUrl = document.getElementById('imageUrl').value;

    const newKobito = { name, description, imageUrl };

    try {
        const response = await fetch(API_BASE_URL, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(newKobito)
        });

        if (response.status === 201) {
            alert('新しいこびとを登録しました！');
            document.getElementById('create-form').reset();
            fetchKobitos();
        } else {
            throw new Error(`HTTPエラー: ${response.status}`);
        }

    } catch (error) {
        displayError(`こびとの登録に失敗しました: ${error.message}`);
    }
});

// ----------------------------------------------------
// 名前検索（画面に表示）
// ----------------------------------------------------
async function searchKobito() {
    const name = document.getElementById("searchName").value;

    if (!name) {
        displayError("検索する名前を入力してください。");
        return;
    }

    try {
        const response = await fetch(`${API_BASE_URL}/search?name=${encodeURIComponent(name)}`);
        if (!response.ok) throw new Error(`HTTPエラー: ${response.status}`);

        const kobitos = await response.json();

        if (kobitos.length === 0) {
            document.getElementById("kobito-list").innerHTML =
                `<p>「${name}」に一致するこびとは見つかりませんでした。</p>`;
            return;
        }

        displayKobitos(kobitos);

    } catch (error) {
        displayError(`名前検索に失敗しました: ${error.message}`);
    }
}

// ----------------------------------------------------
// 編集ページへ遷移
// ----------------------------------------------------
function goEditPage(id) {
    window.location.href = `edit.html?id=${id}`;
}

// ----------------------------------------------------
// 削除
// ----------------------------------------------------
async function deleteKobito(id) {
    const ok = confirm(`ID ${id} のこびとを本当に削除しますか？`);
    if (!ok) return;

    await fetch(`${API_BASE_URL}/${id}`, { method: "DELETE" });

    alert("削除しました！");
    fetchKobitos();
}

// ----------------------------------------------------
// 初期表示
// ----------------------------------------------------
window.onload = () => {
    fetchKobitos();
};
