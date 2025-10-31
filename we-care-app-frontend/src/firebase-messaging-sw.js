/* Firebase SW (served at /firebase-messaging-sw.js) */
//firebase service worker downloaded from firebase website
importScripts('https://www.gstatic.com/firebasejs/9.23.0/firebase-app-compat.js');
importScripts('https://www.gstatic.com/firebasejs/9.23.0/firebase-messaging-compat.js');

firebase.initializeApp({
    apiKey: 'AIzaSyDaqRIoaN5Nr1wrJzpoFsDJx74qHZL4iVI',
    authDomain: 'we-care-app-c1cef.firebaseapp.com',
    projectId: 'we-care-app-c1cef',
    storageBucket: 'we-care-app-c1cef.firebasestorage.app',
    messagingSenderId: '691771603774',
    appId: '1:691771603774:web:20f010d9115f2715ef4a6f',
});

const messaging = firebase.messaging();


// Optional: show background notifications
messaging.onBackgroundMessage((payload) => {
  const title = payload?.notification?.title || 'New Notification';
  const options = {
    body: payload?.notification?.body || '',
    data: payload?.data || {},
  };
  self.registration.showNotification(title, options);
});

// Optional: handle click
self.addEventListener('notificationclick', (event) => {
  event.notification.close();
  const target = event.notification?.data?.url || '/';
  event.waitUntil(clients.openWindow(target));
});




