import { Injectable } from '@angular/core';
import { initializeApp } from 'firebase/app';
import { getMessaging, getToken, onMessage, Messaging } from 'firebase/messaging';
import { environment } from '../../firebase-env/environment';
@Injectable({
  providedIn: 'root'
})
export class PushNotificationService {

  private messaging?: Messaging;
  private currentToken: string | null = null;
  private initDone = false;

  private initFirebase() {
    if (this.initDone) return;
    const app = initializeApp(environment.firebase);
    this.messaging = getMessaging(app);
    this.initDone = true;
  }

  /**
   * Get (or request) the FCM token for this browser.
   * Returns null if user denies permission or something fails.
   */
  async getOrRequestToken(): Promise<string | null> {
    try {
      this.initFirebase();

      // Ask browser permission (must be triggered by a user action ideally)
      const permission = await Notification.requestPermission();
      if (permission !== 'granted') {
        console.warn('Notification permission not granted by the user.');
        return null;
      }

      const token = await getToken(this.messaging!, {
        vapidKey: environment.firebase.vapidKey,
        serviceWorkerRegistration: await navigator.serviceWorker.ready
      });

      if (token) {
        this.currentToken = token;
        return token;
      } else {
        console.warn('No registration token available.');
        return null;
      }
    } catch (err) {
      console.error('Error while retrieving FCM token:', err);
      return null;
    }
  }

  /**
   * Listen to foreground messages (while tab is open).
   */
  listenForegroundMessages(callback: (payload: any) => void) {
    this.initFirebase();
    onMessage(this.messaging!, (payload) => callback(payload));
  }

  getCachedToken(): string | null {
    return this.currentToken;
  }
}
