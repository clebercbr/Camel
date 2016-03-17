/**
 * @author Cranefield, Stephen
 * @author Roloff, Mario Lucio 
 * @author Amaral, Cleber Jorge
 * 
 * Based on and with acknowledgments:
 * camel-agent (camel_jason) 2013 by Stephen Cranefield and Surangika Ranathunga
 * camel-opc (opcada component) 2013/2014 by Justin Smith
 * 
 * It is free software: you can redistribute it and/or modify
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *                                             
 * It is distributed in the hope that it will be useful,                  
 * but WITHOUT ANY WARRANTY; without even the implied warranty of                  
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the                  
 * GNU Lesser General Public License for more details.                             
 * You should have received a copy of the GNU Lesser General Public License        
 * along with camel_jason.  If not, see <http://www.gnu.org/licenses/>.            
 */

package camelartifact.test;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.Processor;
import org.apache.camel.Exchange;

import camelartifact.*;


public class LiteralCollectionTest {

	static Long counter;
	public static void main(String[] args) throws Exception {

		final CamelContext camel = new DefaultCamelContext();
		camel.addComponent("agent", new ArtifactComponent());

		/* Create the routes */
		
		camel.addRoutes(new RouteBuilder() {
			@Override
			public void configure() {
				from("timer:test?period=200").process(new Processor() {
					public void process(Exchange exchange) throws Exception {
						counter = exchange.getProperty(Exchange.TIMER_COUNTER, Long.class);
					}
				}).to("agent:percept?persistent=false&updateMode=replace");
			}
		});

		// start routing
		camel.start();
		System.out.println("Starting router...");


		Thread thread = new Thread() {
			public void run() {
				try {
					System.out.printf("Running...%d",counter);
					while (true) ;
				} catch (Exception e) {
				}
			}
		};
		thread.start();


		System.out.println("... ready.");
	}

}