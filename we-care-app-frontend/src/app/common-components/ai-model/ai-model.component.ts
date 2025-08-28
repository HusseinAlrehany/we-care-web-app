import { Component } from '@angular/core';
import { MaterialModule } from '../../Material.module';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SharedService } from '../shared-service';

@Component({
  selector: 'app-ai-model',
  standalone: true,
  imports: [MaterialModule, CommonModule, FormsModule],
  templateUrl: './ai-model.component.html',
  styleUrl: './ai-model.component.scss'
})
export class AiModelComponent {

  userInput: string = '';
  sessionId: string = 'default-session';
  loading: boolean = false;

  messages: { sender: 'user' | 'ai', text: string } [] = [
    { sender: 'ai', text: 'Hello! I am your We-Care AI assistant. How can I help you today?'}
  ];

  constructor(private sharedService: SharedService){

  }

  sendMessage(){

    if(!this.userInput.trim()) return;

    const query = this.userInput;
    this.messages.push({ sender: 'user', text: query});
    this.userInput = '';
    this.loading = true;


    this.sharedService.chatWithCohereAiModel(query, this.sessionId).subscribe({
      next: (res)=> {
          this.loading = false;
          this.messages.push({ sender: 'ai', text: res.text })
      },
      error:()=> {

        this.loading = false;
        this.messages.push({ sender: 'ai', text: 'Oops! Something went wrong. Please try again.' });

      }
    })
  }

}
