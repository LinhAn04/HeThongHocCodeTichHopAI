// open edit history for comments.css
document.addEventListener('click', function (e) {
    const edited = e.target.closest('.edited-link');
    if (!edited) return;

    const id = edited.dataset.id;
    const source = document.getElementById('history-' + id);

    if (!source) {
        console.warn('No history found for id:', id);
        return;
    }

    document.getElementById('editHistoryContent').innerHTML =
        source.innerHTML;

    document.getElementById('editHistoryOverlay')
        .classList.remove('hidden');
});

document.getElementById('closeHistory')
    ?.addEventListener('click', () => {
        document.getElementById('editHistoryOverlay')
            .classList.add('hidden');
    });

document.getElementById('editHistoryOverlay')
    ?.addEventListener('click', e => {
        if (e.target.id === 'editHistoryOverlay') {
            e.target.classList.add('hidden');
        }
    });

// toggle replies
document.querySelectorAll('.view-replies').forEach(btn => {
    btn.addEventListener('click', () => {
        const id = btn.dataset.id;
        const replies = document.getElementById('replies-' + id);

        if (!replies) return;

        const isHidden = replies.classList.contains('hidden');

        // toggle
        replies.classList.toggle('hidden');

        // đổi text
        if (isHidden) {
            btn.textContent = 'Hide replies';
        } else {
            const count = replies.children.length;
            btn.innerHTML = `View all <span>${count}</span> replies`;
        }
    });
});

document.addEventListener('click', function (e) {
    if (e.target.id === 'closeHistory' ||
        e.target.id === 'editHistoryOverlay') {
        document.getElementById('editHistoryOverlay')
            .classList.add('hidden');
    }
});

