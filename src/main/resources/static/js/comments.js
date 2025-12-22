document.addEventListener('DOMContentLoaded', () => {

    // Toggle reply form
    document.addEventListener('click', e => {
        const btn = e.target.closest('.reply-toggle');
        if (!btn) return;

        const id = btn.dataset.id;
        const form = document.getElementById('reply-form-' + id);
        if (!form) return;

        form.classList.toggle('hidden');
    });

    // Toggle replies list
    document.addEventListener('click', e => {
        const btn = e.target.closest('.view-replies');
        if (!btn) return;

        const id = btn.dataset.id;
        const replies = document.getElementById('replies-' + id);
        if (!replies) return;

        replies.classList.toggle('hidden');
    });

    // Open edit history (comment + reply)
    document.addEventListener('click', e => {
        const edited = e.target.closest('.edited-link');
        if (!edited) return;

        const id = edited.dataset.id;
        const source = document.getElementById('history-' + id);
        if (!source) return;

        document.getElementById('editHistoryContent').innerHTML = source.innerHTML;
        document.getElementById('editHistoryOverlay').classList.remove('hidden');
    });

    // nút X
    document.getElementById('closeHistory')
        ?.addEventListener('click', () => {
            document.getElementById('editHistoryOverlay')
                .classList.add('hidden');
        });

    // click nền tối
    document.getElementById('editHistoryOverlay')
        ?.addEventListener('click', e => {
            if (e.target.id === 'editHistoryOverlay') {
                e.target.classList.add('hidden');
            }
        });

    // toggle menu ⋯
    document.addEventListener('click', function (e) {
        if (e.target.closest('.menu-dropdown')) {
            return;
        }

        // đóng tất cả menu
        document.querySelectorAll('.menu-dropdown')
            .forEach(m => m.classList.add('hidden'));

        const menu = e.target.closest('.comment-menu');
        if (!menu) return;

        const dropdown = menu.querySelector('.menu-dropdown');
        if (!dropdown) return;

        e.stopPropagation();
        dropdown.classList.toggle('hidden');
    });

    // click Edit comment
    document.addEventListener('click', e => {
        const edit = e.target.closest('.edit-comment');
        if (!edit) return;

        const id = edit.dataset.id;

        // ẩn menu
        edit.closest('.menu-dropdown')?.classList.add('hidden');

        // toggle form edit
        const form = document.getElementById('edit-comment-form-' + id);
        if (form) form.classList.toggle('hidden');
    });

    // click Edit reply
    document.addEventListener('click', e => {
        const edit = e.target.closest('.edit-reply');
        if (!edit) return;

        const id = edit.dataset.id;

        edit.closest('.menu-dropdown')?.classList.add('hidden');

        const form = document.getElementById('edit-reply-form-' + id);
        if (form) form.classList.toggle('hidden');
    });

    // delete logic
    let pendingDeleteForm = null;

    // click Delete trong menu
    document.addEventListener('click', e => {
        const btn = e.target.closest('.menu-item.delete');
        if (!btn) return;

        e.preventDefault();
        e.stopImmediatePropagation();

        const form = btn.closest('form.delete-form');
        if (!form) return;

        pendingDeleteForm = form;

        const type = form.dataset.type;
        document.getElementById('deleteTitle').textContent =
            type === 'reply' ? 'Delete reply' : 'Delete comment';

        document.getElementById('deleteMessage').textContent =
            type === 'reply'
                ? 'Are you sure you want to delete this reply?'
                : 'Are you sure you want to delete this comment?';

        document.getElementById('deleteConfirmOverlay')
            .classList.remove('hidden');
    });

    // Confirm delete
    document.getElementById('confirmDelete')
        ?.addEventListener('click', () => {
            if (pendingDeleteForm) {
                pendingDeleteForm.submit();
            }
        });

    // Cancel / Close
    ['closeDelete', 'cancelDelete'].forEach(id => {
        document.getElementById(id)
            ?.addEventListener('click', () => {
                document.getElementById('deleteConfirmOverlay')
                    .classList.add('hidden');
                pendingDeleteForm = null;
            });
    });

    // click nền tối
    document.getElementById('deleteConfirmOverlay')
        ?.addEventListener('click', e => {
            if (e.target.id === 'deleteConfirmOverlay') {
                e.target.classList.add('hidden');
                pendingDeleteForm = null;
            }
        });
});
