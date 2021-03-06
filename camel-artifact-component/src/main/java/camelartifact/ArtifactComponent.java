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

package camelartifact;

import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.camel.Endpoint;
import org.apache.camel.impl.DefaultComponent;

public class ArtifactComponent extends DefaultComponent {

	private ConcurrentLinkedQueue<OpRequest> incomingOpQueue;
	private ConcurrentLinkedQueue<OpRequest> outgoingOpQueue;

	public ArtifactComponent(ConcurrentLinkedQueue<OpRequest> incomingOpQueue,
			ConcurrentLinkedQueue<OpRequest> outgoingOpQueue) {
		this.incomingOpQueue = incomingOpQueue;
		this.outgoingOpQueue = outgoingOpQueue;
	}

	protected Endpoint createEndpoint(String uri, String remaining, Map<String, Object> parameters) throws Exception {
		Endpoint endpoint = new ArtifactEndpoint(uri, this, this.incomingOpQueue, this.outgoingOpQueue);
		setProperties(endpoint, parameters);

		return endpoint;
	}
}
