
Ticket Service Code

Required Software:

	JDK 1.8, Gradle(4.0 or latest)

Technologies used 

	Java, Spring (Boot, Spring JDBC etc) Swagger (API Documentation), H2 (Database)and Liquibase 

Manually testing application (Refer Ticket Service API Documentation below)

How to build and run application

	gradle clean bootrun -Dspring.profiles.active=dev

	Go to : http://localhost:8084/v1/swagger-ui.html

How to build and run tests

	gradle clean build test -DconcurrentTests=false


How to run TicketService test concurrently simulating with 50 concurrent users

	gradle clean build test -DconcurrentTests=true

Ticket Service API Documentation ( http://localhost:8084/v1/swagger-ui.html )

	Swagger API for Ticket Service

	Commonly used words and phrases in this document 

	Venue	 :	The place where something happens, especially an organized event such as a concert, conference, or sports event.
	Event	 :	The event is a time-bound session created for concert, conference, or sports event. Event will have Event Title ,Start Date and End Date

Steps
----------------------------------------------------------------------------------------------
Venue API:  To add venue, find and list all venues and update venue

	1) Try adding the venue (Venue Section - POST)
		Request:
		{
		  "levelsInVenue": 3,
		  "seatsInLevel": 3,
		  "venueLocation": "2202 SW I St, Bentonville, AR 72712",
		  "venueName": "The Ballroom At I Street"
		}
		Response: 
		Note venueID from response 
		{
		  "key": 1 (Venue ID)
		}

	2) To list all the venues use  GET /venues/findAll 

		Response :
		[
		  {
		    "venueName": "The Ballroom At I Street",
		    "venueLocation": "2202 SW I St, Bentonville, AR 72712",
		    "levelsInVenue": 3,
		    "seatsInLevel": 3,
		    "venueId": 1
		  }
		]
	3) To update venue details PUT /venues/{venueId}
	Request
	{
	  "levelsInVenue": 3,
	  "seatsInLevel": 3,
	  "venueLocation": "2203 SW I St, Bentonville, AR 72712",
	  "venueName": "The Ballroom At I Street"
	}
	Query param : venueId = 1 (or ID from add venue response)
	

Events API : To add event, list events by venueID and to update events

	1) Add new event to venue POST /events
		
		Request:
		{
		  "eventDescription": "Christmas Event",
		  "eventEndDateTime": "2017-12-25 18:00:00",
		  "eventStartDateTime": "2017-12-25 10:00:00",
		  "venueId": 1
		}
		Response:
		{
		  "key": 1 (Event ID)
		}
		(Note the above key)
	2) Find events by venue ID GET /events/findByVenue
		Request param (Venue ID): 1 (or venue ID from add venue response)
		
		Response :
		[
		  {
		    "eventDescription": "Christmas Event",
		    "eventStartDateTime": "2017-12-25 10:00:00.0",
		    "eventEndDateTime": "2017-12-25 18:00:00.0",
		    "eventId": 1
		  }
		]
	
	3) Update event is possible with PUT /events/{eventId}
		
		Request param (Event ID) : 1 (or event ID from add event response)
		Request:
		{
		  "eventDescription": "Christmas Event 1",
		  "eventEndDateTime": "2017-12-25 18:00:00",
		  "eventStartDateTime": "2017-12-25 10:00:00",
		  "venueId": 1
		}
		Response: 200 OK (We can find update status using Find events by venue ID)
		
Tickets Api: To find and hold the best available seats on behalf of a customer, Get Event and Venue details along with seat status, number of unreserved seats and reserve and commit a specific group of held seats for a customer

	1) Find and hold the best available seats on behalf of a customer POST /bookings
		Request :
		{
		  "customerEmail": "vasanthkumar.km@gmail.com",
		  "eventId": 1,
		  "numberOfSeats": 4
		}
		Response:
		{
		  "hold-ref": "bb41a55e-0c2c-48d7-9a77-667a01717b7a"
		}
		
		
	2) Event and Venue details along with seat status, number of unreserved seats GET /bookings/findByEvent
		Request param (Event ID): 1 (or event ID from add event response)
		Response:
		{
		  "venueDetails": {
		    "venueName": "The Ballroom At I Street",
		    "venueLocation": "2203 SW I St, Bentonville, AR 72712",
		    "levelsInVenue": 3,
		    "seatsInLevel": 3,
		    "venueId": 1
		  },
		  "eventDetails": {
		    "eventDescription": "Christmas Event 1",
		    "eventStartDateTime": "2017-12-25 10:00:00.0",
		    "eventEndDateTime": "2017-12-25 18:00:00.0",
		    "eventId": 1
		  },
		  "numberOfSeatsAvailable": 5,
		  "reservationStatus": [
		    [
		      "HOLD",
		      "HOLD",
		      "HOLD"
		    ],
		    [
		      "HOLD",
		      "OPEN",
		      "OPEN"
		    ],
		    [
		      "OPEN",
		      "OPEN",
		      "OPEN"
		    ]
		  ]
		}
	
	3) Reserve and commit a specific group of held seats for a customer PUT /bookings/{bookingId}
		Request param (Booking ID): bb41a55e-0c2c-48d7-9a77-667a01717b7a (Please refer response hold-ref ID from POST /bookings)
		Response: 200 (Please feel free to verify booking status with GET /bookings/findByEvent)
		
		
Note : HOLD on tickets will be reset every 5 minutes (Refer holdExpirationInMinutes: 5 in application.yml)
