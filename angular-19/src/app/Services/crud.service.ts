import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { rxResource } from '@angular/core/rxjs-interop';
import { Iuser } from '../iuser';
import { catchError, Observable, retry, throwError, timeout } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CrudService {

  // base_url : string = "http://localhost:3000/Users";
  private readonly base_url =  "/api/tutorials"
  private readonly requestTimeout = 10000; // 10s

  constructor(private http : HttpClient) { }

  // getData(){
  //   // return this.http.get<Iuser[]>(this.base_url);
  //   const data = this.http.get<any>("http://localhost:8082/api/tutorials")
  //   console.log(data)
  //   return data
  // }

  private handleError(error: HttpErrorResponse) {
    let errorMessage = 'An unknown error occurred';
    
    if (error.error instanceof ErrorEvent) {
      // Client-side error
      errorMessage = `Error: ${error.error.message}`;
    } else if (error.status === 0) {
      errorMessage = 'Network error: Could not connect to API';
    } else {
      // Server-side error (WebFlux returns error JSON)
      errorMessage = error.error.message || error.statusText;
    }
    
    console.error(`API Error: ${errorMessage}`);
    return throwError(() => new Error(errorMessage));
  }

  getData(): Observable<any[]> {
    const data = this.http.get<any[]>(`http://localhost:8082/api/tutorials`)
    .pipe(
      timeout(this.requestTimeout),
      retry(2), // 2 tentatives max
      catchError(this.handleError)
    );
    console.log(data)
    return data
  }

  // rxResource = rxResource({
  //   loader : () => this.http.get(this.base_url)
  // })

  postData(data : Iuser){
    return this.http.post(this.base_url,data);
  }

  getDataById(id : number){
    return this.http.get<Iuser>(`${this.base_url}/${id}`);
  }

  putDataById(id : number, data: Iuser){
    return this.http.put(`${this.base_url}/${id}`,data);
  }

  deleteData(id: number){
    return this.http.delete(`${this.base_url}/${id}`);
  }
}
