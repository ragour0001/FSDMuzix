import { TestBed } from '@angular/core/testing';

import { MuzixService } from './muzix.service';
import { Track } from './track';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

describe('MuzixService', () => {
  let track = new Track();
  track = {
    trackId: "track123",
    name: "trackname",
    listners: "123",
    url: "new url",
    comments: "new comments",
    artist: {
      artstId: 123,
      name: "artistname",
      url: "new url",
      image: {
        imageId: 123,
        text: "aaa",
        size: "23"
      }
    }
  };

  const springEndPoint = "http://localhost:8087/usertrackservice/api/v1/usertrackservice/";
  let muzixService: MuzixService;
  let httpTestingController: HttpTestingController;

  beforeEach(() => { 
    TestBed.configureTestingModule({

      imports: [HttpClientTestingModule],
      providers: [MuzixService]
    });
    muzixService = TestBed.get(MuzixService);
    httpTestingController = TestBed.get(HttpTestingController);
  });

  it('should be created', () => {
    // const service: MuzixService = TestBed.get(MuzixService);
    expect(muzixService).toBeTruthy();
  });

  it('#addTrackToWishList() should fetch proper response from Http call', () => {
    muzixService.addTrackToWishList(track).subscribe(res => {
      console.log(res);
      expect(res.body).toBe(track);
    });
    const url = springEndPoint + "user/" + "test" + "/track";
    const httpMockReq = httpTestingController.expectOne(url);
    expect(httpMockReq.request.method).toBe('POST');
    expect(httpMockReq.request.url).toEqual(url);
  });

  it('#getAllTracksforWishList() should fetch proper response from Http call', () => {
    muzixService.getAllTracksforWishList().subscribe(res => {
    });
    const url = springEndPoint + "user/" + "test" + "/tracks";
    const httpMockReq = httpTestingController.expectOne(url);
    expect(httpMockReq.request.method).toBe('GET');
    expect(httpMockReq.request.url).toEqual(url);
  });

  it('#deleteTrackFromWishList() should fetch proper response from Http call', () => {
    muzixService.deleteTrackFromWishList(track).subscribe(res => {
    
    });
    const url = springEndPoint + "user/" + "test" + "/" +track.trackId;
    const httpMockReq = httpTestingController.expectOne(url);
    expect(httpMockReq.request.method).toBe('DELETE');
    expect(httpMockReq.request.url).toEqual(url);
  });

  it('#updateComments() should fetch proper response from Http call', () => {
    muzixService.updateComments(track).subscribe(res => {
      expect(res.body).toBe(track);
    });
    const url = springEndPoint + "user/" + "test" + "/track";
    const httpMockReq = httpTestingController.expectOne(url);
    expect(httpMockReq.request.method).toBe('PATCH');
    expect(httpMockReq.request.url).toEqual(url);
  });

});
